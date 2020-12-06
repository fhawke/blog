package com.star.entity;

import lombok.Data;

@Data
public class UserMail {
    private Integer id;
    private String username;
    private String password;
    private String useremail;
    private Integer status;
    private String code;
    private String avatar;
}
