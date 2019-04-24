package com.nanshanlive.entity;

/**
 * @Author zhang
 * @Date 2019/4/11 16:11
 * @Content
 */
public class AnchorRoom {

    private String anchorName;
    private RoomEntity roomEntity;
    private LiveCatEntity liveCatEntity;
    private String liveAddr;

    public String getLiveAddr() {
        return liveAddr;
    }

    public void setLiveAddr(String liveAddr) {
        this.liveAddr = liveAddr;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public LiveCatEntity getLiveCatEntity() {
        return liveCatEntity;
    }

    public void setLiveCatEntity(LiveCatEntity liveCatEntity) {
        this.liveCatEntity = liveCatEntity;
    }
}
