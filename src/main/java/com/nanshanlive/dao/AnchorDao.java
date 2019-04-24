package com.nanshanlive.dao;

import com.nanshanlive.entity.AnchorEntity;
import com.nanshanlive.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author zhang
 * @Date 2019/4/3 14:25
 * @Content
 */
public interface AnchorDao extends CrudRepository<AnchorEntity,Integer> {
    public AnchorEntity findByUid(int uid);
}
