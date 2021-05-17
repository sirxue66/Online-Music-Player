package com.javaclimb.musicplayer.dao;

import com.javaclimb.musicplayer.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 歌手dao层接口
* 声明@Repository之后，service层可以方便调用
*/
@Repository
public interface SingerMapper {
    /**
     * 增加
     */
    public int insert(Singer singer);
    /**
     * 修改
     */
    public int update(Singer singer);
    /**
     * 删除
     */
    public int delete(Integer id);
    /**
     * 主键查询
     */
    public Singer selectById(Integer id);
    /**
     * 查询所有
     */
    public List<Singer> selectAllSingers();
    /**
     * 歌手名字 模糊查询
     */
    public List<Singer> selectByName(String name);
    /**
     * 歌手性别 模糊查询
     */
    public List<Singer> selectBySex(Integer sex);

}
