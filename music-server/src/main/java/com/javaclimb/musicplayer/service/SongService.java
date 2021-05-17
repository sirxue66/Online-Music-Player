package com.javaclimb.musicplayer.service;

import com.javaclimb.musicplayer.domain.Song;

import java.util.List;

/**
 * 歌曲service层
 */
public interface SongService {
    /**
     *增加
     */
    public boolean insert(Song song);

    /**
     *修改
     */
    public boolean update(Song song);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Song selectSongById(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌名模糊查询列表
     */
    public List<Song> selectSongByName(String name);

    /**
     * 根据歌手id查询
     */
    public List<Song> selectSongBySingerId(Integer singerId);
}
