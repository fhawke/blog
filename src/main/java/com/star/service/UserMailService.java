package com.star.service;

        import com.star.entity.UserMail;

public interface UserMailService {
    void register(UserMail userMail);

    UserMail checkCode(String code);

    void updateUserStatus(UserMail userMail);

    UserMail loginUserMail(UserMail userMail);
}
