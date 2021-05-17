package com.javaclimb.musicplayer.dao;


import com.javaclimb.musicplayer.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 歌单dao层接口
* 声明@Repository之后，service层可以方便调用
*/
@Repository
public interface SongListMapper {
    /**
     *增加
     */
    public int insert(SongList songList);

    /**
     *修改
     */
    public int update(SongList SongList);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public SongList selectSongListById(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<SongList> allSongList();

    /**
     * 根据 标题 查询歌单列表
     */
    public List<SongList> selectSongListByTitle(String title);

    /**
     * 根据歌单名模糊查询列表
     */
    public List<SongList> selectSongListLikeTitle(String title);

    /**
     * 根据歌单风格查询歌单列表
     */
    public List<SongList> selectSongListByStyle(String style);

}
