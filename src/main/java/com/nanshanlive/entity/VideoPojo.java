package com.nanshanlive.entity;

/**
 * @Author zhang
 * @Date 2019/4/21 13:36
 * @Content
 */
public class VideoPojo {

    private String videoAdd;
    private RoomEntity roomEntity;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoAdd() {
        return videoAdd;
    }

    public void setVideoAdd(String videoAdd) {
        this.videoAdd = videoAdd;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
