package com.javaclimb.musicplayer.dao;

import org.springframework.stereotype.Repository;

/**
* 管理员dao层接口
* 声明@Repository之后，service层可以方便调用
*/
@Repository
public interface AdminMapper {
    /*验证密码的方法*/
    public int verifyPassword(String username,String password);
}
