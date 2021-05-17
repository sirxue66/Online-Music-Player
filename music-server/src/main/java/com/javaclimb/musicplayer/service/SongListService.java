package com.javaclimb.musicplayer.service;

import com.javaclimb.musicplayer.domain.SongList;

import java.util.List;

/**
 * 歌单service层
 */
public interface SongListService  {
    /**
     *增加
     */
    public boolean insert(SongList songList);

    /**
     *修改
     */
    public boolean update(SongList songList);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public SongList selectSongListById(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> allSongList();

    /**
     * 根据标题精确查询歌单列表
     */
    public List<SongList> selectSongListByTitle(String title);

    /**
     * 根据标题模糊查询歌单列表
     */
    public List<SongList> selectSongListLikeTitle(String title);

    /**
     * 根据风格模糊查询歌单列表
     */
    public List<SongList> selectSongListByStyle(String style);
}
