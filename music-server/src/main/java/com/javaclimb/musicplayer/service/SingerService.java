package com.javaclimb.musicplayer.service;

import com.javaclimb.musicplayer.domain.Singer;

import java.util.List;

/**
* 歌单service层接口
*/
public interface SingerService {
    /**
     * 增加
     */
    public boolean insert(Singer singer);
    /**
     * 修改
     */
    public boolean update(Singer singer);
    /**
     * 删除
     */
    public boolean delete(Integer id);
    /**
     * 主键查询
     */
    public Singer selectById(Integer id);
    /**
     * 查询所有
     */
    public List<Singer> selectAllSingers();
    /**
     * 歌手名字 模糊查询
     */
    public List<Singer> selectByName(String name);
    /**
     * 歌手性别 模糊查询
     */
    public List<Singer> selectBySex(Integer sex);
}
