package com.star.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类实体类，和博客一对多
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Type {
    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();
}
