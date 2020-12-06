package com.star.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体类，和评论一对多关系
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Blog {
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer commentCount;
    private Boolean appreciation;
    private Boolean shareStatement;
    private Boolean commentabled;
    private Boolean published;
    private Boolean recommend;
    private Date createTime;
    private Date updateTime;
    private String description;

    private Type type;
    private User user;
    private UserMail userMail;
    private Long typeId;
    private Long userId;
    private Long userMailId;
    private List<Comment> comments = new ArrayList<>();
}
