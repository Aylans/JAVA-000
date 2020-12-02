package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * created by Aylan
 * on 2020/12/2 8:24 下午
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("masterJdbcTemplate")
    private JdbcTemplate masterJdbcTemplate;
    @Autowired
    @Qualifier("slave1JdbcTemplate")
    private JdbcTemplate slave1JdbcTemplate;

    public List<Map<String, Object>> getUsers(){
        String sql = "select * from users";
        return slave1JdbcTemplate.queryForList(sql);
    }

}
