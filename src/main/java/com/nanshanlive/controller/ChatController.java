package com.nanshanlive.controller;

import com.nanshanlive.dao.StatDao;
import com.nanshanlive.dao.UserDao;
import com.nanshanlive.entity.Guest;
import com.nanshanlive.entity.UserEntity;
import com.nanshanlive.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhanghl on 2019/4/12.
 */
@Controller
@RequestMapping("/")
public class ChatController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StatDao statDao;
    @Autowired
    @Lazy
    private SimpMessagingTemplate simpMessagingTemplate;
    /*@RequestMapping(value = "/live",method = RequestMethod.GET)
    public String hello(){
        return "live";
    }*/
    @RequestMapping(value = "/live2",method = RequestMethod.GET)
    public String hello2(HttpServletRequest request){
        System.out.println(request.getSession().getId());
        return "live2";
    }




    @RequestMapping(value = "/ip",method = RequestMethod.GET)
    @ResponseBody
    public String getIp(HttpServletRequest request){
        return IpUtil.getIp(request);
    }


    @RequestMapping(value = "/chatroom",method = RequestMethod.GET)
    public String testChatRoom(){
        return "websocket";
    }


    @RequestMapping(value = "/jpa",method = RequestMethod.GET)
    @ResponseBody
    public UserEntity testJpa(@RequestParam(value = "name",required = true) String username ,
                              HttpServletRequest request){
        UserEntity entity = new UserEntity();
        entity.setName(username);
        entity.setIp(IpUtil.getIp(request));
        return userDao.save(entity);
    }

    /*Principal principal ,存放用户的登录验证信息
    Message message，最基础的消息体，里面方有header和payload等信息
    @Payload 消息体内容
    @Header(“..”) 某个头部key的值
    @Headers, 所有头部key的map集合
    MessageHeaders , SimpMessageHeaderAccessor, MessageHeaderAccessor ,StompHeaderAccessor 消息头信息
    @DestinationVariable 类似springmvc中的@PathVariable*/
    @MessageMapping(value = "/live")
    @SendTo("/topic/group")
    public String testWst(String message){

        return message;
    }

    /**
     * 统计在线人数
     * @param ip
     * @return
     */
    @RequestMapping(value = "/online_counts",method = RequestMethod.GET)
    @ResponseBody
    public String  pushCounts(@RequestParam(value = "ip",required = true) String ip){
        UserEntity userEntity = new UserEntity();
        userEntity.setIp(ip);
        userEntity.setName("test");
        statDao.pushOnlineUser(userEntity);
        return "ok";
    }

    /**
     *
     * @param ip
     * @return
     */
    @RequestMapping(value = "/online_counts",method = RequestMethod.DELETE)
    @ResponseBody
    public String  popCounts(@RequestParam(value = "ip",required = true) String ip){
        UserEntity userEntity = new UserEntity();
        userEntity.setIp(ip);
        userEntity.setName("test");
        statDao.popOnlineUser(userEntity);
        return "ok";
    }


    @RequestMapping(value = "/guest",method = RequestMethod.GET)
    @ResponseBody
    public void pushGuest(@RequestParam(value = "ip",required = true) String ip){
        UserEntity userEntity = new UserEntity();
        userEntity.setIp(ip);
        userEntity.setName("test");
        Guest guest = new Guest();
        guest.setUserEntity(userEntity);
        guest.setAccessTime(Calendar.getInstance().getTimeInMillis());
        statDao.pushGuestHistory(guest);
    }

    /**
     * 获取所有观众
     * @return
     */
    @RequestMapping(value = "/all_guest",method = RequestMethod.GET)
    @ResponseBody
    public List AllGuest(){
        return statDao.getGuestHistory();
    }

    /**
     * 聊天室向消息中间件MQ发送消息
     * @return
     */
    @RequestMapping(value= "/send", method = RequestMethod.GET)
    @ResponseBody
    public String send() {

        this.simpMessagingTemplate.convertAndSend("/topic/online_user","老师发布了一个通知");
        //this.template.convertAndSend("/discuss/replymesg/666666", "有人回复了你");
        return "服务器发布消息";
    }
}
