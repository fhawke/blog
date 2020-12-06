package com.star.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 搜索博客管理列表
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchBlog {
    private String title;
    private Long typeId;
}
