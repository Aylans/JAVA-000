package java;

import com.aylan.models.Order;
import com.aylan.service.OrderService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * created by Aylan
 * on 2020/12/9 11:14 下午
 */
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private OrderService orderService;

    @Test
    public void test(){
        Order order1 = new Order(1000L,1L);
        Order order2 = new Order(1001L,2L);
        List<Order> orders = Arrays.asList(order1,order2);
        orderService.saveBatch(orders);
        List<Order> orderList = orderService.list();
        orderList.forEach(System.out::println);
    }
}
