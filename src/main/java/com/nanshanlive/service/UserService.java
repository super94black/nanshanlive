package com.nanshanlive.service;

import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.util.IpUtil;
import com.nanshanlive.util.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author zhang
 * @Date 2019/4/3 12:41
 * @Content
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 用户注册
     * @param userEntity
     * @param request
     */
    public boolean register(UserEntity userEntity, HttpServletRequest request)throws Exception{
        userEntity.setPass(MDUtil.md5(userEntity.getPass()));
        userEntity.setIp(IpUtil.getIp(request));
        userEntity.setCreateTime(new Date());
        userEntity.setIsAnchor(0);
        userEntity.setUpdateTime(new Date());
        userDao.save(userEntity);
        return true;
    }

    /**
     * 用户登陆验证
     * @param userEntity
     * @return
     */
    public UserEntity login(UserEntity userEntity) throws Exception {
        UserEntity daoUser = userDao.findByName(userEntity.getName());
        if(null == daoUser || null == daoUser.getName() || null == daoUser.getPass()
        || !daoUser.getPass().equals(MDUtil.md5(userEntity.getPass()))){
            return null;
        }

        return daoUser;
    }


    public UserEntity findByName(String name){
        return userDao.findByName(name);
    }


}
