package com.aylan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by Aylan
 * on 2020/11/18 6:59 下午
 */
@Configuration
public class ConfigurationDemo {


    public ConfigurationDemo() {
        System.out.println("ConfigurationDemo容器初始化开始...");
    }

    @Bean
    public PromptBean descBean(){
        return new PromptBean();
    }

}
