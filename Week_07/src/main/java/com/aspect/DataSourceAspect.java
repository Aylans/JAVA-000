package com.aspect;

import com.annotation.Database;
import com.config.DataSourceConstant;
import com.config.DynamicDataSourceConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * created by Aylan
 * on 2020/12/2 8:45 下午
 */
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.annotation.Database)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        Database ds = method.getAnnotation(Database.class);
        if(ds != null){
            if(ds.readOnly()){
                // 负载均衡，简单随即
                final String slave = loadBalance();
                System.out.println("随机：" + slave);
                DynamicDataSourceConfig.setDataSource(slave);
            }else{
                DynamicDataSourceConfig.setDataSource(DataSourceConstant.MASTER);
            }
        }else{
            DynamicDataSourceConfig.setDataSource(DataSourceConstant.SLAVE1);
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSourceConfig.clearDataSource();
        }
    }

    private String loadBalance(){
        Random random = new Random();
        final int i = random.nextInt(2) + 1;
        switch (i){
            case 1:
                return DataSourceConstant.SLAVE1;
            case 2:
                return DataSourceConstant.SLAVE2;
        }
        return DataSourceConstant.SLAVE1;
    }

    public static void main(String[] args) {
        DataSourceAspect s = new DataSourceAspect();
        for(int i=0;i<10;i++){
            s.loadBalance();
        }
    }
}
