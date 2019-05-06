package com.nanshanlive.controller;

import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.service.UserService;
import com.nanshanlive.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author zhang
 * @Date 2019/4/10 21:18
 * @Content 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public Result register(UserEntity userEntity, HttpServletRequest request){
        Result result = new Result();
        try {
            if(null == userEntity.getName() || null == userEntity.getPass()){
                result.setStatus(400);
                result.setMsg("请完善个人信息");
                return result;
            }

            UserEntity dbUser = userService.findByName(userEntity.getName());
            if (null == dbUser || "".equals(dbUser.getName())){
                userService.register(userEntity,request);
                request.getSession().setAttribute("user",dbUser);
                return Result.ok("注册成功");
            }else {
                result.setStatus(400);
                result.setMsg("用户已被注册");
                return result;
            }

        }catch (Exception e){
            result.setStatus(500);
            result.setMsg("服务器出错");

        }
        return result;
    }

    /**
     * 用户登陆
     * @param userEntity
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(UserEntity userEntity,HttpServletRequest request){
        Result result = new Result();
        try {
            if(null == userEntity.getName() || null == userEntity.getPass()){
                result.setStatus(400);
                result.setMsg("请完善个人信息");
                return result;
            }
            UserEntity user = userService.login(userEntity);
            if(null == user || user.getName().equals("")){
                result.setMsg("用户信息不正确");
                result.setStatus(400);
                return result;
            }
            //登陆成功用户放入session
            request.getSession().setAttribute("user",user);
            return Result.ok();
        }catch (Exception e){
            result.setStatus(500);
            result.setMsg("内部错误");
        }

        return result;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
