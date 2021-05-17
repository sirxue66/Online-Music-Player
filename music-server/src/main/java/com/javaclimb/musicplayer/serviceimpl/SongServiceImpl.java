package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.SongMapper;
import com.javaclimb.musicplayer.domain.Song;
import com.javaclimb.musicplayer.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    /**
     * 增加
     *
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    /**
     * 修改
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Song selectSongById(Integer id) {
        return songMapper.selectSongById(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    /**
     * 根据歌名模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Song> selectSongByName(String name) {
        return songMapper.selectSongByName("%"+name+"%");
    }

    /**
     * 根据歌手id查询
     *
     * @param singerId
     */
    @Override
    public List<Song> selectSongBySingerId(Integer singerId) {
        return songMapper.selectSongBySingerId(singerId);
    }
}
