package com.javaclimb.musicplayer.dao;

import com.javaclimb.musicplayer.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 歌曲dao层接口
* 声明@Repository之后，service层可以方便调用
*/
@Repository
public interface SongMapper {
    /**
     *增加
     */
    public int insert(Song song);

    /**
     *修改
     */
    public int update(Song song);

    /**
     * 删除
     */
    public int delete(Integer id);

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
