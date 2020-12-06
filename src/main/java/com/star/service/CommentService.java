package com.star.service;

import com.star.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);

    void deleteComment(Comment comment,Long id);
}
