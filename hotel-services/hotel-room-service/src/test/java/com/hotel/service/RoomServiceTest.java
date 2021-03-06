package com.hotel.service;

import com.hotel.room.entity.Room;
import com.hotel.room.entity.RoomFloor;
import com.hotel.room.entity.RoomType;
import com.hotel.room.enums.RoomStateEnum;
import com.hotel.room.service.RoomFloorService;
import com.hotel.room.service.RoomService;
import com.hotel.room.service.RoomTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Import(HotelRoomServiceApplication.class)
public class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Autowired
    RoomFloorService roomFloorService;

    @Autowired
    RoomTypeService roomTypeService;

    /**
     * 初始化房间
     * 每层
     */
    @Test
    public void initData() {
        List<RoomFloor> roomFloors = roomFloorService.findAll();
        List<RoomType> roomTypes = roomTypeService.findAll();
        Date now = new Date();
        for(RoomFloor roomFloor: roomFloors) {
            for(RoomType roomType: roomTypes) {
                Room room = new Room();
                room.setRoomFloor(roomFloor);
                room.setRoomType(roomType);

                int roomNumber = roomFloor.getFloor() * 100 + roomType.getId();
                room.setNumber(roomNumber);
                room.setAlias(roomNumber + "房");

                room.setCharge(roomType.getCharge());
                room.setDeposit(roomType.getDeposit());

                room.setState(RoomStateEnum.EMPTY.name());

                room.setCreateTime(now);

                room.setCreateUserId(1L);
                room.setUpdateTime(now);
                room.setUpdateUserId(1L);
                roomService.save(room);
            }
        }
    }
}
