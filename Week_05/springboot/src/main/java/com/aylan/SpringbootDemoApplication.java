package com.aylan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * created by Aylan
 * on 2020/11/18 5:30 下午
 */
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationDemo.class);
        PromptBean promptBean = (PromptBean)context.getBean("descBean");
        promptBean.sayHello();
    }
}
