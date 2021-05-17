package com.javaclimb.musicplayer.domain;

import java.io.Serializable;

/**
 * 评价domain
 */
public class Rank implements Serializable {
    private Integer id;
    private Integer songListId;
    private Integer consumerId;
    /*评分*/
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }
}
