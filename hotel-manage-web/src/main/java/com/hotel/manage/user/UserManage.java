package com.hotel.manage.user;

import com.hotel.user.api.UserApi;
import com.hotel.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManage {

    @Autowired
    UserApi userApi;

    public User findOne(Long id) {
        return userApi.findOne(id);
    }

    public User findByUsername(String username) {
        return userApi.findByUsername(username);
    }
}