package io.kimmking.rpcfx.demo.api.rpc;

import io.kimmking.rpcfx.demo.api.dto.Order;

public interface OrderService {

    Order findOrderById(int id);

}
