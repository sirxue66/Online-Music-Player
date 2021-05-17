package com.javaclimb.musicplayer.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.musicplayer.domain.Singer;
import com.javaclimb.musicplayer.domain.Song;
import com.javaclimb.musicplayer.service.SongService;
import com.javaclimb.musicplayer.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 歌曲controller层
 */
@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
//        获取前端参数
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = "./img/songPic/songPic.png";
        String lyric = request.getParameter("lyric").trim();
//        上传歌曲文件
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"请添加歌曲文件");
            return jsonObject;
        };
//        如果歌曲文件过大，处理
        if (mpFile.getSize()>100000000){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲文件过大，上传失败");
            return jsonObject;
        }
//      如果成功，命名文件名=时间戳+原文件名
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
//        文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
//        如果路径不存在，就新增路径
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
//        实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
//                jsonObject.put("avator",storeUrlPath);
                return jsonObject;
            }
            else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;}
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 查询所有歌曲
     */
    @RequestMapping(value = "/allsongs",method = RequestMethod.GET)
    public Object SearchAllSongs(HttpServletRequest request){
        return songService.allSong();
    }

    /**
     * 查询歌手下的歌曲
     */
    @RequestMapping(value = "/singer/song",method = RequestMethod.GET)
    public Object SearchSongBySingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId").trim();
        return songService.selectSongBySingerId(Integer.parseInt(singerId));
    }

    /**
     * 根据歌曲id查询歌曲
     */
    @RequestMapping(value = "/song",method = RequestMethod.GET)
    public Object SearchSongById(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        return songService.selectSongById(Integer.parseInt(songId));
    }

    /**
     * 根据歌曲名字模糊查询歌曲列表
     */
    @RequestMapping(value = "/searchSong",method = RequestMethod.GET)
    public Object SearchSongLikeName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return songService.selectSongByName(name);
    }

    /**
     * 修改歌曲信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //trim() 方法用于删除字符串的头尾空白符
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();//专辑
        String lyric = request.getParameter("lyric").trim();
        //保存到歌手的对象中
        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag = songService.update(song);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    /**
     * 删除歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSong(HttpServletRequest request){
//        TODO  删除只是简单删除了数据库中的数据，未删除文件中的文件
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        boolean flag = songService.delete(Integer.parseInt(id));  //parseInt() 方法用于将字符串参数作为有符号的十进制整数进行解析。
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"删除成功");
            return jsonObject;
        } else {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"删除失败");
            return jsonObject;
        }
    }

    /**
     * 更新歌曲图片
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    /*传入参数  文件名 和 id*/
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"请添加图片文件");
            return jsonObject;
        }
        //文件名=当前时间到毫秒（时间戳）+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

}




















