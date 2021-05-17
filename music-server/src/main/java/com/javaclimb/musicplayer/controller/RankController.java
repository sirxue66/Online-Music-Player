package com.javaclimb.musicplayer.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.musicplayer.domain.Rank;
import com.javaclimb.musicplayer.service.RankService;
import com.javaclimb.musicplayer.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 评价controller层
 */
@RestController
public class RankController {
    @Autowired
    private RankService rankService;

    /**
     * 增加评价
     * TODO 用户重复评价会报错，添加方法在重复时直接返回错误类型
     */
    @RequestMapping(value = "/rank/add",method = RequestMethod.POST)
    public Object add(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String songListId = request.getParameter("songListId");
        String consumerId = request.getParameter("consumerId");
        String score = request.getParameter("score");
        Rank rank = new Rank();
        rank.setSongListId(Integer.parseInt(songListId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));
        boolean flag = rankService.insert(rank);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }

    /**
     * 计算平均分
     */
    @RequestMapping(path = "/rank",method = RequestMethod.GET)
    public Object averageRank(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return rankService.averageRank(Integer.parseInt(songListId));
    }





}
