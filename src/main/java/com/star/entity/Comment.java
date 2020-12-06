package com.star.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论实体类，和子评论一对多
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    private Long blogId;
    private Long parentCommentId;
    private Boolean adminComment;

    //回复评论
    private List<Comment> replyComments = new ArrayList<>();
    private Comment ParentComment;
    private String parentNickname;
}
