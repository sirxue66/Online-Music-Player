package com.javaclimb.musicplayer.dao;

import com.javaclimb.musicplayer.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论dao层
 */
@Repository
public interface CommentMapper {
    /**
     *增加
     */
    public int insert(Comment comment);

    /**
     *修改
     */
    public int update(Comment comment);

    /**
     * 删除
     */
    public int delete(Integer id);

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
