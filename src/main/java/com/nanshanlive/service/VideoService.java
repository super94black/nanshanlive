package com.nanshanlive.service;

import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.*;
import com.nanshanlive.thread.FormatVideoThread;
import com.nanshanlive.dao.RoomDao;
import com.nanshanlive.dao.VideoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/21 13:26
 * @Content
 */
@Service
public class VideoService {

    public static String name;

    @Autowired
    VideoDao videoDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 找出所有可以公开的视频
     * @return
     */
    public List<VideoPojo> getAllVideo(){
        //找出所有可以公开的视频
        List<VideoEntity> list = videoDao.findAllByType(1);
        if(null == list || list.size() == 0)
            return null;
        List<VideoPojo> res = new ArrayList<>();
        RoomEntity roomEntity = new RoomEntity();
        UserEntity userEntity = new UserEntity();
        for (VideoEntity videoEntity :list) {
            roomEntity = roomDao.findOne(videoEntity.getRid());
            userEntity = userDao.findOne(roomEntity.getUid());
            VideoPojo pojo = new VideoPojo();
            pojo.setVideoAdd(videoEntity.getVideoAdd());
            pojo.setRoomEntity(roomEntity);
            pojo.setName(userEntity.getName());
            res.add(pojo);
        }
        return res;
    }

    /**
     * 转换视频格式
     * @param recordDone
     * @throws Exception
     */
    public void formatVideo(RecordDone recordDone){
        try{
            String token = (String) redisTemplate.opsForValue().get(recordDone.getName());
            String videoName = recordDone.getName() + "-" + token.split("\\?")[1];
            FormatVideoThread _360pThread = new FormatVideoThread("100k",recordDone.getName(),"360p//",videoName);
            _360pThread.start();
            FormatVideoThread _720pThread = new FormatVideoThread("600k",recordDone.getName(),"720p//",videoName);
            _720pThread.start();

            Integer uid = Integer.parseInt(recordDone.getName());
            RoomEntity roomEntity = roomDao.findByUidAndType(uid,1);
            //写入数据库
            VideoEntity videoEntity = new VideoEntity();
            String videoAdd = videoName + ".flv";
            videoEntity.setVideoAdd(videoAdd);
            videoEntity.setRid(roomEntity.getId());
            videoEntity.setCreateTime(new Date());
            videoEntity.setType(1);
            videoDao.save(videoEntity);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 根据videoid获得视频
     * @param id
     * @return
     */
    public VideoPojo findVideoById(Integer id){
        VideoEntity videoEntity = videoDao.findOne(id);
        RoomEntity roomEntity = roomDao.findOne(videoEntity.getRid());
        VideoPojo pojo = new VideoPojo();
        pojo.setVideoAdd(videoEntity.getVideoAdd());
        pojo.setRoomEntity(roomEntity);
        return pojo;
    }


}
