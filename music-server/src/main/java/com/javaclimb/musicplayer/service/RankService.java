package com.javaclimb.musicplayer.service;

import com.javaclimb.musicplayer.domain.Rank;

/**
 *评价service层
 */
public interface RankService {
    /**
     *增加
     */
    public boolean insert(Rank rank);

    /**
     * 查总分
     */
    public int selectAllScore(Integer songListId);

    /**
     * 查总评分人数
     */
    public int selectAllConsumer(Integer songListId);

    /**
     * 计算平均分
     */
    public int averageRank(Integer songListId);
}
