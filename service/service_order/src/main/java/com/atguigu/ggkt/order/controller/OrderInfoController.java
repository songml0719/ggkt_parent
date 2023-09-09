package com.atguigu.ggkt.order.controller;


import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.order.service.OrderInfoService;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author songml
 * @since 20230401
 */
@RestController
@RequestMapping(value = "/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("订单列表")
    @GetMapping("{page}/{limit}")
    public Result listOrder(@PathVariable Long page,
                            @PathVariable Long limit,
                            OrderInfoQueryVo orderInfoQueryVo) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Map<String, Object> map =
                orderInfoService.selectOrderInfoPage(pageParam, orderInfoQueryVo);
        return Result.ok(map);
    }
}

