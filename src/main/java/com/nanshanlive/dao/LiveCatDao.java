package com.nanshanlive.dao;

import com.nanshanlive.entity.LiveCatEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author zhang
 * @Date 2019/4/11 16:21
 * @Content
 */
public interface LiveCatDao  extends CrudRepository<LiveCatEntity,Integer> {
}
