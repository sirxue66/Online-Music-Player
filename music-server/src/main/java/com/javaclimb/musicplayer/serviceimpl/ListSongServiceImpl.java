package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.ListSongMapper;
import com.javaclimb.musicplayer.domain.ListSong;
import com.javaclimb.musicplayer.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单中歌曲servoce实现类
 *
 */
@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;


    /**
     * 增加
     *
     * @param listSong
     */
    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }

    /**
     * 修改
     *
     * @param listSong
     */
    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return listSongMapper.delete(id)>0;
    }

    /**
     * 根据歌曲id和歌单id删除
     *
     * @param songId
     * @param songListId
     */
    @Override
    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId) {
        return listSongMapper.deleteBySongIdAndSongListId(songId,songListId)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public ListSong selectListSongById(Integer id) {
        return listSongMapper.selectListSongById(id);
    }

    /**
     * 查询所有歌单里面的歌曲
     */
    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    /**
     * 根据歌单id查询所有的歌曲
     *
     * @param songListId
     */
    @Override
    public List<ListSong> selectSongByListSongId(Integer songListId) {
        return listSongMapper.selectSongByListSongId(songListId);
    }
}
