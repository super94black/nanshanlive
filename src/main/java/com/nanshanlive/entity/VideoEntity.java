package com.nanshanlive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhang
 * @Date 2019/4/21 13:29
 * @Content
 */
@Entity
@Table(name = "video")
public class VideoEntity implements Serializable {

    private static final long serialVersionUID = 1l;
    private int id;
    private String videoAdd;
    private int rid;
    private Date createTime;
    private Date updateTime;
    private int type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "video_add")
    public String getVideoAdd() {
        return videoAdd;
    }

    public void setVideoAdd(String videoAdd) {
        this.videoAdd = videoAdd;
    }


    @Column(name = "rid")
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
