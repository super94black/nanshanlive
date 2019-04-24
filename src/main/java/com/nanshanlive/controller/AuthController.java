package com.nanshanlive.controller;
import com.nanshanlive.service.AuthService;
import com.nanshanlive.service.LiveService;
import com.nanshanlive.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @Author zhang
 * @Date 2019/4/2 16:43
 * @Content 直播流鉴权
 */
@Controller
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    LiveService liveService;
    @Autowired
    RoomService roomService;

    @PostMapping("/auth")
    public ResponseEntity checkLiveStream(String name, String pass){
        System.out.println(name + " " + pass);
        boolean flag = authService.authLiveStream(name,pass);
        if(flag){
            //主播如果已经开启了一个直播流 直接拒绝再次开启直播间
            if(authService.authExistLiveRoomNow(Integer.valueOf(name)))
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            //没有的话直接在redis中开启一个直播间
            liveService.addRedisLiveRoom(name,pass);
            //添加直播开始时间
            roomService.addRoomStartTime(Integer.valueOf(name));
            //截图
            liveService.getLiveStreamImg(Integer.valueOf(name),pass);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
