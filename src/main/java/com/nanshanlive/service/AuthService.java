package com.nanshanlive.service;

import com.nanshanlive.dao.AnchorDao;
import com.nanshanlive.entity.AnchorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2019/4/3 17:28
 * @Content
 */
@Service
public class AuthService {

    @Autowired
    AnchorDao anchorDao;
    @Autowired
    RedisTemplate redisTemplate;


    //判断直播流token是否符合
    public boolean authLiveStream(String name,String pass){
        if(null == name || null == pass)
            return false;
        Integer uid = Integer.valueOf(name);
        AnchorEntity anchorEntity = anchorDao.findByUid(uid);
        String token = uid + "?pass=" + pass;
        if(null == anchorEntity || !token.equals(anchorEntity.getToken()) || anchorEntity.getType() != 1)
            return false;
        return true;
    }

    /**
     * 判断该主播是否已经开启了直播间
     * @param uid
     * @return
     */
    public boolean authExistLiveRoomNow(Integer uid){
        boolean flag = redisTemplate.opsForSet().isMember("NowLiveRoom",String.valueOf(uid));
        if(flag)
            return true;
        return false;
    }


}
