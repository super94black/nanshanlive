package com.nanshanlive.service;

import com.nanshanlive.dao.AnchorDao;
import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.AnchorEntity;
import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.util.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;


/**
 * @Author zhang
 * @Date 2019/4/3 14:15
 * @Content
 */
@Service
public class AnchorService {

    @Autowired
    AnchorDao anchorDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;

    //主播获取直播流地址
    public String getLiveToken(Integer uid) throws Exception {
        if(null == uid)
            return null;

        String token = null;
        String liveStream = null;
        String id = String.valueOf(uid);
        AnchorEntity anchorEntity = anchorDao.findByUid(uid);

        //生成token
        if(null == anchorEntity){
            liveStream = MDUtil.md5(String.valueOf(UUID.randomUUID()));
            token = id + "?pass=" + liveStream;

            anchorEntity = new AnchorEntity();
            anchorEntity.setUid(uid);
            anchorEntity.setRoomNumber(uid);
            anchorEntity.setCreateTime(new Date());
            anchorEntity.setToken(token);
            anchorEntity.setType(1);
            anchorDao.save(anchorEntity);
        }
        else{
            token = anchorEntity.getToken();
        }


        redisTemplate.opsForValue().set(String.valueOf(uid),token);
        return token;
    }

    /**
     * 判断是不是主播
     * @param uid
     * @return
     */
    public boolean isAnchor(Integer uid){
        AnchorEntity anchorEntity = anchorDao.findOne(uid);

        if(anchorEntity == null)
            return false;

        UserEntity userEntity = userDao.findOne(uid);
        if(null == userEntity || userEntity.getIsAnchor() == 0)
            return false;
        return true;
    }


}
