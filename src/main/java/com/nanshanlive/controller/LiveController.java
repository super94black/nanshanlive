package com.nanshanlive.controller;

import com.nanshanlive.dao.StatDao;
import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.MsgEntity;
import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.util.IpUtil;
import com.nanshanlive.util.NameGenerator;
import com.nanshanlive.util.UserAgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by zhang on 2019/3/14.
 * 直播间控制器
 */
@Controller
public class LiveController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StatDao statDao;
    @RequestMapping(value = "/live_room/{roomNumber}",method = RequestMethod.GET)
    public String watchLive(@PathVariable Integer roomNumber, HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        //用户处于未登录状态
        if(null == user){
            user = new UserEntity();
            user.setName("游客__" + NameGenerator.generate());
        }else{
            user.setPass(null);
        }
        session.setAttribute("user",user);
        model.addAttribute("roomNumber",roomNumber);
        model.addAttribute("online_guests",getOnlineUser());
        model.addAttribute("history_guests",getHistoryGuests());
        return "live";

    }


    @RequestMapping(value = "/online_guests",method = RequestMethod.GET)
    @ResponseBody
    public Set getOnlineUser(){
        return  statDao.getAllUserOnline();
    }


    @RequestMapping(value = "/history_guests",method = RequestMethod.GET)
    @ResponseBody
    public List getHistoryGuests(){
        return statDao.getGuestHistory();
    }


    @MessageMapping(value = "/chat")
    @SendTo("/topic/group")
    public MsgEntity testWst(String message , @Header(value = "simpSessionAttributes") Map<String,Object> session){
        UserEntity user = (UserEntity) session.get("user");
        String username = user.getName();
        MsgEntity msg = new MsgEntity();
        msg.setCreator(username);
        msg.setsTime(Calendar.getInstance());
        msg.setMsgBody(message);
        return msg;
    }


}
