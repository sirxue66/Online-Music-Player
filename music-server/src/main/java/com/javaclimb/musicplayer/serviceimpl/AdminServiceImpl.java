package com.javaclimb.musicplayer.serviceimpl;

import com.javaclimb.musicplayer.dao.AdminMapper;
import com.javaclimb.musicplayer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员service实现类
 * @Service 声明这是一个service
 */
@Service
public class AdminServiceImpl implements AdminService {
    /**
     * 注入dao层的AdminMapper
     */
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 验证密码的方法
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username,password) > 0;
    }
}
