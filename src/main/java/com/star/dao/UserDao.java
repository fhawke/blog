package com.star.dao;

import com.star.entity.User;
import com.star.entity.UserMail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    /**
     * @param username  用户名
     * @param password  密码
     * @return  返回用户对象
     */
    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    /**
     * 返回插入是否成功 0/1
     * @param user  用户对线
     * @return
     */
    int  insert(User user);

    /**
     *
     */
    UserMail findByUserMail(@Param("username") String username,@Param("password") String password);
}
