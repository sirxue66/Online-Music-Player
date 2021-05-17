package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.SongListMapper;
import com.javaclimb.musicplayer.domain.SongList;
import com.javaclimb.musicplayer.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单service实现类
 */
@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;
    /**
     * 增加
     *
     * @param songList
     */
    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList)>0;
    }

    /**
     * 修改
     *
     * @param songList
     */
    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public SongList selectSongListById(Integer id) {
        return songListMapper.selectSongListById(id);
    }

    /**
     * 查询所有歌单
     */
    @Override
    public List<SongList> allSongList() {
        return songListMapper.allSongList();
    }

    /**
     * 根据标题精确查询歌单列表
     *
     * @param title
     */
    @Override
    public List<SongList> selectSongListByTitle(String title) {
        return songListMapper.selectSongListByTitle(title);
    }

    /**
     * 根据标题模糊查询歌单列表
     *
     * @param title
     */
    @Override
    public List<SongList> selectSongListLikeTitle(String title) {
        return songListMapper.selectSongListLikeTitle(title);
    }

    /**
     * 根据风格模糊查询歌单列表
     *
     * @param style
     */
    @Override
    public List<SongList> selectSongListByStyle(String style) {
        return songListMapper.selectSongListByStyle(style);
    }
}
