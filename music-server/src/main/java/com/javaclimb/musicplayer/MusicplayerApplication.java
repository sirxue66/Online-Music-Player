package com.javaclimb.musicplayer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 开启热加载方法： ctrl+shift+A   搜索registry...   然后勾选  compiler.automake.allow.when.app.running
* 快捷键 ctrl+F9  执行编译 热加载
*
* */

@SpringBootApplication
@MapperScan("com.javaclimb.musicplayer.dao")
public class MusicplayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicplayerApplication.class, args);
    }

}
