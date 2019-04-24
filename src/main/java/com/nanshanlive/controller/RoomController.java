package com.nanshanlive.controller;

import com.nanshanlive.entity.AnchorRoom;
import com.nanshanlive.entity.RoomEntity;
import com.nanshanlive.service.RoomService;
import com.nanshanlive.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/11 15:44
 * @Content
 */
@Controller
public class RoomController {

    @Autowired
    RoomService roomService;

    /**
     * 主播在直播前申请开启一个房间
     * @param roomEntity
     * @return
     */
    @PostMapping("/room")
    public Result addLiveRoom(RoomEntity roomEntity){
        Result result;
        try {
            result = roomService.addRoomInfo(roomEntity);

        }catch (Exception e){
            return Result.error();
        }
        return result;
    }

    @GetMapping("/room")
    @ResponseBody
    public Result getAllLiveRoom(){
        List<AnchorRoom> list = roomService.getAllLiveRoom();
        return Result.ok(list);
    }
}
