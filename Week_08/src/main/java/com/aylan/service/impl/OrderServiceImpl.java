package com.aylan.service.impl;

import com.aylan.mapper.OrderMapper;
import com.aylan.models.Order;
import com.aylan.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * created by Aylan
 * on 2020/12/9 10:59 下午
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
