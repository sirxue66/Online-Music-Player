package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.RankMapper;
import com.javaclimb.musicplayer.domain.Rank;
import com.javaclimb.musicplayer.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评价service实现类
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;
    /**
     * 增加
     *
     * @param rank
     */
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    /**
     * 查总分
     *
     * @param songListId
     */
    @Override
    public int selectAllScore(Integer songListId) {
        return rankMapper.selectAllScore(songListId);
    }

    /**
     * 查总评分人数
     *
     * @param songListId
     */
    @Override
    public int selectAllConsumer(Integer songListId) {
        return rankMapper.selectAllConsumer(songListId);
    }

    /**
     * 计算平均分
     *
     * @param songListId
     */
    @Override
    public int averageRank(Integer songListId) {
        int rankNum = rankMapper.selectAllConsumer(songListId);
        if (rankNum == 0){
            return 5;
        }
        return rankMapper.selectAllScore(songListId)/rankNum;
    }
}
