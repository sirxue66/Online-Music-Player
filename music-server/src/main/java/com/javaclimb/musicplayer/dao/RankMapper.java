package com.javaclimb.musicplayer.dao;

import com.javaclimb.musicplayer.domain.Rank;
import org.springframework.stereotype.Repository;

/**
 * 评价dao层
 */
@Repository
public interface RankMapper {
    /**
     * 增加
     */
    public int insert(Rank rank);
    /**
     * 查询歌单的总评价分
     */
    public int selectAllScore(Integer songListId);
    /**
     * 查询歌单下的评价人数
     */
    public int selectAllConsumer(Integer songListId);
}
