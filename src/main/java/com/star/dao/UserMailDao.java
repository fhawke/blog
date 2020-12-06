package com.star.dao;

import com.star.entity.UserMail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMailDao {

    void register(UserMail userMail);

    UserMail checkCode(String code);

    void updateUserStatus(UserMail userMail);

    UserMail loginUserMail(UserMail userMail);
}
