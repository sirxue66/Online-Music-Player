package com.javaclimb.musicplayer.service;

/**
* 管理员service层接口
*/
public interface AdminService {
    /**
     * 验证密码的方法
     */
    public boolean verifyPassword(String username,String password);
}
