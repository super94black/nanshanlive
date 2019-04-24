package com.nanshanlive.dao;

import com.nanshanlive.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/21 13:34
 * @Content
 */
public interface VideoDao extends CrudRepository<VideoEntity,Integer> {

    List<VideoEntity> findAllByType(int type);
}
