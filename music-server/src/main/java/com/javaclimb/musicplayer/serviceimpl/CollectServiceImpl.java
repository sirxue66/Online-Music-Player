package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.CollectMapper;
import com.javaclimb.musicplayer.domain.Collect;
import com.javaclimb.musicplayer.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏Service实现类
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }

    /**
     * 根据用户id和歌曲id删除收藏
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean deleteBySongId(Integer userId, Integer songId) {
        return collectMapper.deleteBySongId(userId,songId)>0;
    }

    /**
     * 查询所有收藏
     */
    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    /**
     * 查询某个用户的收藏
     *
     * @param userId
     */
    @Override
    public List<Collect> selectByUserId(Integer userId) {
        return collectMapper.selectByUserId(userId);
    }

    /**
     * 查询某个用户是否已经收藏了某个歌曲
     *
     * @param userId
     * @param songId
     * @param标签提供了对某个函数的参数的各项说明，包括参数名、参数数据类型、描述等
     */
    @Override
    public boolean exitSongId(Integer userId, Integer songId) {
        return collectMapper.exitSongId(userId,songId)>0;
    }
}
