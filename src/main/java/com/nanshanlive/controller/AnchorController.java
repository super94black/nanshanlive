package com.nanshanlive.controller;

import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.service.AnchorService;
import com.nanshanlive.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhang
 * @Date 2019/4/3 14:05
 * @Content 主播控制器
 */
@Controller
public class AnchorController {

    @Autowired
    AnchorService anchorService;

    @PostMapping("/token")
    @ResponseBody
    public Result getLiveToken(@RequestParam Integer id){

        try {
            if(null == id || 0 == id)
                return Result.error();
            System.out.println("hi");
            return Result.ok(anchorService.getLiveToken(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }

    @PostMapping("/becomeAnchor")
    @ResponseBody
    public Result becomeAnchor(@RequestParam Integer id, HttpServletRequest request){
        anchorService.becomeAnchor(id);
        UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
        userEntity.setIsAnchor(1);
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("user",userEntity);
        return Result.ok();
    }

}
