package com.star.service;

import com.star.entity.*;

import java.util.List;

public interface BlogService {
    int saveBlog(Blog blog);
    List<BlogQuery> getAllBlog();
    void deleteBlog(Long id);
    ShowBlog getBlogById(Long id);
    int updateBlog(ShowBlog showBlog);
    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);
    List<FirstPageBlog> getFirstPageBlog();
    List<RecommendBlog> getAllRecommendBlog();
    List<FirstPageBlog> getSearchBlog(String query);
    Integer getBlogTotal();
    Integer getBlogViewTotal();
    Integer getBlogCommentTotal();
    Integer getBlogMessageTotal();
    DetailBlog getDetailedBlog(Long id);
    List<FirstPageBlog> getByTypeId(Long typeId);
    Blog selectBlogById(Long id);
    Long getUserIdById(Long id);
}
