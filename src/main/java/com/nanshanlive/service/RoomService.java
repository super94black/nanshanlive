package com.nanshanlive.service;

import com.nanshanlive.dao.RoomDao;
import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.AnchorRoom;
import com.nanshanlive.entity.RoomEntity;
import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @Author zhang
 * @Date 2019/4/10 21:57
 * @Content
 */
@Service
public class RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserDao userDao;

    /**
     * 主播申请开启一个直播房间，添加房间信息
     * @param roomEntity
     */
    public Result addRoomInfo(RoomEntity roomEntity){
        Result result = new Result();
        //保证每个主播每次只能开启一个直播间
        RoomEntity daoUser = roomDao.findByUidAndType(roomEntity.getUid(),1);
        if (null == daoUser){
            result.setStatus(400);
            result.setMsg("请勿重复申请开启直播间");
            return result;
        }
        roomEntity.setType(1);
        roomDao.save(roomEntity);
        return Result.ok();
    }

    /**
     * 直播开始添加开始时间
     * @param
     */
    public void addRoomStartTime(Integer uid){
        RoomEntity roomEntity = roomDao.findByUidAndType(uid,1);
        roomEntity.setStartTime(new Date());
        roomDao.save(roomEntity);
    }


    /**
     * 获取所有直播间
     * @return
     */
    public List<AnchorRoom> getAllLiveRoom(){
        List<AnchorRoom> list = new ArrayList<>();
        Set set = redisTemplate.opsForSet().members("NowLiveRoom");
        String liveAdd = "http://localhost:8080/live_room/";
        if(null == set || set.size() == 0)
            return list;
        String uid = "";
        RoomEntity roomEntity;
        for (Object o:set) {
            if(o instanceof String){
                uid = (String) o;
                roomEntity = roomDao.findByUidAndType(Integer.valueOf(uid),1);
                if(null != roomEntity){
                    //查询用户表
                    UserEntity userEntity= userDao.findOne(roomEntity.getUid());
                    AnchorRoom anchorRoom = new AnchorRoom();
                    anchorRoom.setAnchorName(userEntity.getName());
                    anchorRoom.setRoomEntity(roomEntity);
                    anchorRoom.setLiveAddr(liveAdd + uid);
                    list.add(anchorRoom);
                }
            }
        }
        return list;
    }


}
