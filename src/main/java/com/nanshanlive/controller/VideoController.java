package com.nanshanlive.controller;

import com.nanshanlive.entity.RecordDone;
import com.nanshanlive.entity.VideoPojo;
import com.nanshanlive.service.LiveService;
import com.nanshanlive.service.RoomService;
import com.nanshanlive.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/21 13:47
 * @Content
 */
@Controller
public class VideoController {

    @Autowired
    VideoService videoService;
    @Autowired
    LiveService liveService;

    @GetMapping("/shows")
    public String getAllVideo(Model model){
        List<VideoPojo> res = videoService.getAllVideo();
        model.addAttribute("res",res);
        return "shows";
    }

    /**
     * 直播结束开始转码
     * @param recordDone
     * @return
     */
    @RequestMapping("/video/format")
    @ResponseBody
    public String formatVideo(RecordDone recordDone){
        System.out.println(recordDone.getPath());
        try {
            //转码码率
            videoService.formatVideo(recordDone);
            //清除redis和mysql中的直播间
            liveService.closeLiveStream(recordDone.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @GetMapping("/video")
    public String getVideoById(Integer id,String type,Model model){
        VideoPojo videoPojo = videoService.findVideoById(id);
        String videoAdd = null;
        String baseAdd = "http://localhost/vod/";
        if("360p".equals(type))
            videoAdd = baseAdd + "360p/" + videoPojo.getVideoAdd();
        else if("720p".equals(type))
            videoAdd = baseAdd + "720p/" + videoPojo.getVideoAdd();
        model.addAttribute("uid",id);
        model.addAttribute("title",videoPojo.getRoomEntity().getrName());
        model.addAttribute("imgAdd",videoPojo.getRoomEntity().getImgAdd());
        model.addAttribute("videoAdd",videoAdd);
        return "video";
    }
}
