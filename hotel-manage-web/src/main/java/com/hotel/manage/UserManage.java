package com.hotel.manage;

import com.hotel.api.UserApi;
import com.hotel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManage {

    @Autowired
    UserApi userApi;

    public User findOne(Long id) {
        return userApi.findOne(id);
    }
}