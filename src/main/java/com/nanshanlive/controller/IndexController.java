package com.nanshanlive.controller;

import com.nanshanlive.entity.AnchorRoom;
import com.nanshanlive.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zhang
 * @Date 2019/4/12 12:42
 * @Content
 */
@Controller
public class IndexController {

    @Autowired
    RoomService roomService;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model){

        List<AnchorRoom> list = roomService.getAllLiveRoom();

        model.addAttribute("list",list);
        return "index";
    }
}
