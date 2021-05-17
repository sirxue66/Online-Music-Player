package com.javaclimb.musicplayer.service;

import com.javaclimb.musicplayer.domain.Comment;

import java.util.List;

/**
 * 评论service层
 */
public interface CommentService {
    /**
     *增加
     */
    public boolean insert(Comment comment);

    /**
     *修改
     */
    public boolean update(Comment comment);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public Comment selectCommentById(Integer id);

    /**
     * 查询所有评论
     */
    public List<Comment> allComments();

    /**
     * 查询某个歌曲下的所有评论
     */
    public List<Comment> selectCommentBySongId(Integer songId);

    /**
     * 查询某个歌单下的所有评论
     */
    public List<Comment> selectCommentBySongListId(Integer songListId);
}

