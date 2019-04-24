package com.nanshanlive.controller;

import com.nanshanlive.service.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2019/4/3 14:05
 * @Content 主播控制器
 */
@Controller
public class AnchorController {

    @Autowired
    AnchorService anchorService;

    @GetMapping("/token")
    @ResponseBody
    public String getLiveToken(Integer uid){

        try {
            System.out.println("hi");
            return anchorService.getLiveToken(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
