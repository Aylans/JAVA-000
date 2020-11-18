package com.aylan.workbean;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@Data
public class School implements ISchool {

    @Resource
    Klass class1;

    @Resource
    Student student100;

    @Override
    public void ding(){
        System.out.println("ding");
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
    }
    
}
