package com.hotel.controller;

import com.hotel.manage.CheckInRecordManage;
import com.hotel.room.constant.SystemConstant;
import com.hotel.room.dto.CheckInRecordQueryDto;
import com.hotel.room.entity.CheckInCustomer;
import com.hotel.room.entity.CheckInRecord;
import com.hotel.room.enums.CheckStateEnum;
import com.hotel.room.enums.EnumListConstant;
import com.hotel.room.utils.AmountUtils;
import com.hotel.vo.ResultVo;
import com.hotel.vo.in.CheckInCustomerVo;
import com.hotel.vo.in.CheckInVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("checkInRecord")
public class CheckInRecordController extends BaseController {

    @Autowired
    CheckInRecordManage checkInRecordManage;

    @PostMapping("findManage")
    @ResponseBody
    @RequiresPermissions("checkInRecord:findManage")
    public ResultVo<?> findManage(@RequestBody CheckInRecordQueryDto queryDto) {
        return ResultVo.success(checkInRecordManage.findManage(queryDto));
    }

    @GetMapping("findOne/{id}")
    @ResponseBody
    @RequiresPermissions("checkInRecord:findOne")
    public ResultVo<?> findOne(@PathVariable("id") Long id) {
        return ResultVo.success(checkInRecordManage.findOne(id));
    }

    @GetMapping("findDetail/{id}")
    @ResponseBody
    @RequiresPermissions("checkInRecord:findDetail")
    public ResultVo<?> findDetail(@PathVariable("id") Long id) {
        return ResultVo.success(checkInRecordManage.findDetail(id));
    }

    @GetMapping("getCheckStateEnums")
    @ResponseBody
    @RequiresPermissions("checkInRecord:getCheckStateEnums")
    public ResultVo<?> getCheckStateEnums() {
        return ResultVo.success(EnumListConstant.getEnumEntryList(CheckStateEnum.class));
    }

    @PostMapping("checkIn")
    @ResponseBody
    @RequiresPermissions("checkInRecord:checkIn")
    public ResultVo<?> checkIn(@Valid @RequestBody CheckInVo checkInVo) {
        CheckInRecord checkInRecord = new CheckInRecord();
        BeanUtils.copyProperties(checkInVo, checkInRecord);
        checkInRecord.setPayedCharge(AmountUtils.toLong(checkInVo.getPayedCharge()));
        checkInRecord.setPayedDeposit(AmountUtils.toLong(checkInVo.getPayedDeposit()));

        checkInRecord.setMemberId(SystemConstant.getId(checkInRecord.getMemberId()));
        long memberId = checkInRecord.getMemberId();
        if(SystemConstant.isNullId(memberId)) {
            // TODO 校验会员是否存在
        }

        List<CheckInCustomerVo> checkInCustomers = checkInVo.getCheckInCustomers();
        checkInRecord.setCheckInCustomers(checkInCustomers.stream().map(vo -> {
            CheckInCustomer checkInCustomer = new CheckInCustomer();
            checkInCustomer.setIdCard(vo.getIdCard());
            checkInCustomer.setMobile(vo.getMobile());
            checkInCustomer.setName(vo.getName());
            return checkInCustomer;
        }).collect(Collectors.toList()));

        return ResultVo.success(checkInRecordManage.checkIn(checkInRecord).getId(), "登记入住成功");
    }

    @GetMapping("reserveCheckIn/{id}")
    @ResponseBody
    @RequiresPermissions("checkInRecord:reserveCheckIn")
    public ResultVo<?> reserveCheckIn(@PathVariable("id") Long id) {
        return ResultVo.success(checkInRecordManage.reserveCheckIn(id), "预约入住成功");
    }

}
