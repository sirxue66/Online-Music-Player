package com.javaclimb.musicplayer.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.musicplayer.service.AdminService;
import com.javaclimb.musicplayer.utils.Consts;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @RestController 声明这是一个controller 并且返回json数据
 */
@RestController
public class AdminController {
    /**
     * 注入AdminService
     */
    @Autowired
    private AdminService adminService;
    /**
     * 判断是否登录状态
     * HttpServletRequest 用来接收前端传的值，HttpSession将登录用户信息存入session
     */
    @RequestMapping(value = "/admin/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest request, HttpSession session){
        /*TODo  使用cookie来实现登录状态的判断与控制*/
        JSONObject jsonObject = new JSONObject();
        /*将前端传来的name，password 拿到*/
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag = adminService.verifyPassword(name, password);
        /*判断查询结果*/
        /*如果查到，表示密码正确，登录成功，返回给前端信息，并且将用户信息存入session*/
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登陆成功");
            session.setAttribute(Consts.NAME,name);
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }
}















