package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.proxy.ByteBuddyProxy;
import io.kimmking.rpcfx.proxy.RpcFxDynamicProxy;
import io.kimmking.rpcfx.demo.api.dto.Order;
import io.kimmking.rpcfx.demo.api.rpc.OrderService;
import io.kimmking.rpcfx.demo.api.dto.User;
import io.kimmking.rpcfx.demo.api.rpc.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

    // 二方库
    // 三方库 lib
    // nexus, userserivce -> userdao -> user
    //

    public static void main(String[] args) {

        // UserService service = new xxx();
        // service.findById

        UserService userService = RpcFxDynamicProxy.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("find user id=1 from server: " + user.getName());

        OrderService orderService = ByteBuddyProxy.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findOrderById(1992129);
        System.out.printf("find order name=%s, amount=%f%n", order.getName(), order.getAmount());

        // 新加一个OrderService

//		SpringApplication.run(RpcfxClientApplication.class, args);
    }

}
