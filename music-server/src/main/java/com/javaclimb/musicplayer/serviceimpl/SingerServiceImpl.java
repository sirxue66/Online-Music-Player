package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.SingerMapper;
import com.javaclimb.musicplayer.domain.Singer;
import com.javaclimb.musicplayer.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌手service实现类
 */
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;

    /**
     * 增加
     *
     * @param singer
     */
    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer) > 0;
    }

    /**
     * 修改
     *
     * @param singer
     */
    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id) > 0;
    }

    /**
     * 主键查询
     *
     * @param id
     */
    @Override
    public Singer selectById(Integer id) {
        return singerMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Singer> selectAllSingers() {
        return singerMapper.selectAllSingers();
    }

    /**
     * 歌手名字 模糊查询
     *
     * @param name
     */
    @Override
    public List<Singer> selectByName(String name) {
        return singerMapper.selectByName(name);
    }

    /**
     * 歌手性别 模糊查询
     *
     * @param sex
     */
    @Override
    public List<Singer> selectBySex(Integer sex) {
        return singerMapper.selectBySex(sex);
    }
}
