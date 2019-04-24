package com.nanshanlive.dao;

import com.nanshanlive.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;


/**
 * @Author zhang
 * @Date 2019/4/10 21:56
 * @Content
 */
public interface RoomDao extends CrudRepository<RoomEntity,Integer> {

    public RoomEntity findByUid(int uid);
    public RoomEntity findByUidAndType(Integer uid,int type);

}
