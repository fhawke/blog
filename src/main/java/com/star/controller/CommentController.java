package com.star.controller;

import com.star.entity.Comment;
import com.star.entity.DetailBlog;
import com.star.entity.User;
import com.star.service.BlogService;
import com.star.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    //新增评论
    //得到用户和评论id
    //如果用户不为空，那么为"楼主"
    //否则赋值一个头像，然后设置评论的父评论，保存评论到容器
    //将容器(评论)赋值给前端页面展示
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session,Model model){
        Long blogId = comment.getBlogId();
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);
        }

        if(comment.getParentComment().getId() != null){
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    //删除评论
    //删除评论后，还需要在跳回blog界面的时候，给前端页面赋值
    //赋值为：新的评论和博客详细内容
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment, RedirectAttributes attributes,
                         Model model){
        commentService.deleteComment(comment,id);
        DetailBlog detailBlog = blogService.getDetailedBlog(blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("blog",detailBlog);
        model.addAttribute("comments",comments);
        return "blog";
    }
}
