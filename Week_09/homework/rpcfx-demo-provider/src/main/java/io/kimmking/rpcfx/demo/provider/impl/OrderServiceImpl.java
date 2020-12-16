package io.kimmking.rpcfx.demo.provider.impl;

import io.kimmking.rpcfx.demo.api.dto.Order;
import io.kimmking.rpcfx.demo.api.rpc.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
