package com.star.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DetailBlog {
    //博客信息
    private Long id;
    private String firstPicture;
    private String flag;
    private String title;
    private String content;
    private Integer views;
    private Integer commentCount;
    private Date updateTime;
    private boolean commentabled;
    private boolean shareStatement;
    private boolean appreciation;
    private String nickname;
    private String avatar;
    //分类名称
    private String typeName;
}
