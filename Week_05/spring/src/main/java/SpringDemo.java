import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by Aylan
 * on 2020/11/18 3:33 下午
 */
public class SpringDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = context.getBean(User.class);
        System.out.println(JSONObject.toJSON(user));
    }
}
