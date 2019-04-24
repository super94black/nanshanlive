package com.nanshanlive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhang
 * @Date 2019/4/10 21:49
 * @Content 直播历史记录表
 */
@Entity
@Table(name = "room")
public class RoomEntity implements Serializable {
    private static final long serialVersionUID = 1l;

    private int id;
    private String rDec;
    private String rName;
    private int uid;
    private String imgAdd;
    private Date startTime;
    private Date endTime;
    private int type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "rdec")
    public String getrDec() {
        return rDec;
    }

    public void setrDec(String rDec) {
        this.rDec = rDec;
    }

    @Column(name = "rname")
    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }




    @Column(name = "img_add")
    public String getImgAdd() {
        return imgAdd;
    }

    public void setImgAdd(String imgAdd) {
        this.imgAdd = imgAdd;
    }


    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
