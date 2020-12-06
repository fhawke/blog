package com.star.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.dao.BlogDao;
import com.star.entity.Comment;
import com.star.entity.DetailBlog;
import com.star.entity.FirstPageBlog;
import com.star.entity.RecommendBlog;
import com.star.service.BlogService;
import com.star.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogService blogService;

//    @Autowired
//    private TypeService typeService;

    @Autowired
    private CommentService commentService;

    //    分页查询博客列表
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, RedirectAttributes attributes){
        PageHelper.startPage(pageNum,10);
        List<FirstPageBlog> allFirstPageBlog = blogService.getFirstPageBlog();
        List<RecommendBlog> recommendedBlog = blogService.getAllRecommendBlog();

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlogs", recommendedBlog);

        return "index";
    }

    //    搜索博客
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query) {
        PageHelper.startPage(pageNum, 1000);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    //    跳转博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailBlog detailedBlog = blogService.getDetailedBlog(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", detailedBlog);
        return "blog";
    }

    //最新博客列表
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        List<FirstPageBlog> newBlog = blogService.getFirstPageBlog();
        model.addAttribute("newblogs", newBlog);
        return "index :: newblogList";
    }

    //    博客信息
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }
}