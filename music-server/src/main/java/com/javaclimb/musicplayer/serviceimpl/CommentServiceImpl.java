package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.CommentMapper;
import com.javaclimb.musicplayer.domain.Comment;
import com.javaclimb.musicplayer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论service实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    /**
     * 增加
     *
     * @param comment
     */
    @Override
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment)>0;
    }

    /**
     * 修改
     *
     * @param comment
     */
    @Override
    public boolean update(Comment comment) {
        return commentMapper.update(comment)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return commentMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Comment selectCommentById(Integer id) {
        return commentMapper.selectCommentById(id);
    }

    /**
     * 查询所有评论
     */
    @Override
    public List<Comment> allComments() {
        return commentMapper.allComments();
    }

    /**
     * 查询某个歌曲下的所有评论
     *
     * @param songId
     */
    @Override
    public List<Comment> selectCommentBySongId(Integer songId) {
        return commentMapper.selectCommentBySongId(songId);
    }

    /**
     * 查询某个歌单下的所有评论
     *
     * @param songListId
     */
    @Override
    public List<Comment> selectCommentBySongListId(Integer songListId) {
        return commentMapper.selectCommentBySongListId(songListId);
    }
}
