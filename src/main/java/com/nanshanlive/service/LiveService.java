package com.nanshanlive.service;

import com.nanshanlive.dao.AnchorDao;
import com.nanshanlive.dao.RoomDao;
import com.nanshanlive.entity.AnchorEntity;
import com.nanshanlive.entity.RoomEntity;
import com.nanshanlive.thread.ImageThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * @Author zhang
 * @Date 2019/4/10 22:39
 * @Content
 */
@Service
public class LiveService {

    public static int staticUid;
    public static String staticToken;
    public static String staticImgAdd;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RoomDao roomDao;
    @Autowired
    AnchorDao anchorDao;


    /**
     * 真正的为主播开启直播流 在redis中记录直播房间号
     * @param uid
     */
    public void addRedisLiveRoom(String uid,String pass){
        //redis中直接记录直播间房间号
        redisTemplate.opsForSet().add("NowLiveRoom",uid);

    }

    /**
     * 对直播流进行截图
     * @param uid
     * @param token
     */
    public void getLiveStreamImg(Integer uid,String token) {
        String imgAddr = "http://localhost/img/"+ uid + "-pass=" + token + ".jpg";
        startLiveStreamImgThread(uid,token);
        //将直播截图地址写回Mysql

        RoomEntity roomEntity = roomDao.findByUidAndType(uid,1);
        roomEntity.setImgAdd(imgAddr);
        roomDao.save(roomEntity);



    }

    /**
     * 对直播流进行截图
     */
    public void startLiveStreamImgThread(Integer uid,String token){
        ImageThread imageThread = new ImageThread(uid,token);
        imageThread.start();
    }

    /**
     * 关闭直播流时把数据库中关于房间的信息做处理
     * @param uid
     */
    public void closeLiveStream(String uid){

        int userId = Integer.parseInt(uid);

        RoomEntity roomEntity = roomDao.findByUidAndType(userId,1);
        roomEntity.setEndTime(new Date());
        roomEntity.setType(0);
        roomDao.save(roomEntity);

        AnchorEntity anchorEntity = anchorDao.findByUid(roomEntity.getUid());
        anchorEntity.setToken("");
        anchorDao.save(anchorEntity);

        redisTemplate.opsForSet().remove("NowLiveRoom",uid);


    }




}
