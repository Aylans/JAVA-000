import com.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * created by Aylan
 * on 2020/12/2 9:02 下午
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void test1(){
        List<Map<String, Object>> list = userService.getUsers();
        System.out.println(list);
    }
}
