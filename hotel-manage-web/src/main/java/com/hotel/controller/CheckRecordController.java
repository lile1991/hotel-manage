package com.hotel.controller;

import com.hotel.dto.CheckRecordQueryDto;
import com.hotel.entity.CheckRecord;
import com.hotel.entity.Customer;
import com.hotel.entity.Room;
import com.hotel.enums.CheckStateEnum;
import com.hotel.enums.EnumListConstant;
import com.hotel.manage.CheckRecordManage;
import com.hotel.vo.CheckInVo;
import com.hotel.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("checkRecord")
public class CheckRecordController extends BaseController {

    @Autowired
    CheckRecordManage checkRecordManage;

    @RequestMapping("findCheckOut/{id}")
    @ResponseBody
    public ResultVo<?> findOne(@PathVariable("id") Long id) {
        return ResultVo.success(checkRecordManage.findCheckOut(id));
    }

    @RequestMapping("findManage")
    @ResponseBody
    public ResultVo<?> findManage(@RequestBody CheckRecordQueryDto queryDto) {
        return ResultVo.success(checkRecordManage.findManage(queryDto));
    }

    @RequestMapping("getCheckStateEnums")
    @ResponseBody
    public ResultVo<?> getCheckStateEnums() {
        return ResultVo.success(EnumListConstant.getEnumEntryList(CheckStateEnum.class));
    }

    @PostMapping("checkIn")
    @ResponseBody
    public ResultVo<?> checkIn(@Valid @RequestBody CheckInVo checkInVo) {
        CheckRecord checkRecord = new CheckRecord();
        BeanUtils.copyProperties(checkInVo, checkRecord);

        Room room = new Room();
        room.setId(checkInVo.getRoomId());
        checkRecord.setRoom(room);

        Long customerId = checkInVo.getCustomerId();

        if(customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            checkRecord.setCustomer(customer);
        }

        return ResultVo.success(checkRecordManage.checkIn(checkRecord), "登记入住成功");
    }

    @RequestMapping("reserveCheckIn/{id}")
    @ResponseBody
    public ResultVo<?> reserveCheckIn(@PathVariable("id") Long id) {
        return ResultVo.success(checkRecordManage.reserveCheckIn(id), "预约入住成功");
    }

    /**
     * 实扣押金、实收房费。。 处理人  加这几个字段。。
     * @param id
     * @return
     */
    @RequestMapping("leave/{id}")
    @ResponseBody
    public ResultVo<?> leave(@PathVariable("id") Long id) {
        return ResultVo.success(checkRecordManage.leave(id), "退房成功");
    }
}
