package com.hotel.manage;

import com.hotel.api.RoomTypeApi;
import com.hotel.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomTypeManage {

    @Autowired
    RoomTypeApi roomTypeApi;

    public List<RoomType> findAll() {
        return roomTypeApi.findAll();
    }
}
