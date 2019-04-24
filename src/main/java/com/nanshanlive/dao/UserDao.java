package com.nanshanlive.dao;

import com.nanshanlive.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */

public interface UserDao extends CrudRepository<UserEntity,Integer>{

    public UserEntity findByName(String name);
}
