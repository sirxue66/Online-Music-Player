package com.javaclimb.musicplayer.dao;

import com.javaclimb.musicplayer.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏dao层
 */
@Repository
public interface CollectMapper {
    /**
     * 增加
     */
    public int insert(Collect collect);
    /**
     * 根据主键删除
     */
    public int delete(Integer id);
    /**
     * 根据用户id和歌曲id删除收藏
     */
    public int deleteBySongId( Integer userId, Integer songId);
    /**
     * 查询所有收藏
     */
    public List<Collect> allCollect();
    /**
     * 查询某个用户的收藏
     */
    public List<Collect> selectByUserId(Integer userId);
    /**
     * 查询某个用户是否已经收藏了某个歌曲
     * @param标签提供了对某个函数的参数的各项说明，包括参数名、参数数据类型、描述等
     */
    public int exitSongId(@Param("userId") Integer userId,@Param("songId") Integer songId);
}
