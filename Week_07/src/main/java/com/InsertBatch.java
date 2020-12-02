package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * created by Aylan
 * on 2020/12/2 7:29 下午
 */
public class InsertBatch {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final int insert = insert();
        System.out.println("成功插入："+insert);
    }

    public static int insert() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/config",
                "config","config@123");
        connection.setAutoCommit(false); //设置手动提交
        String sql = "insert into users (name,age,gender) values (?,?,?)";
        final PreparedStatement ps = connection.prepareStatement(sql);
        for(int i = 1; i<=1000000; i++){
            ps.setString(1, String.format("用户名%s",i));
            ps.setInt(2, i%50==0?1:i%50);
            ps.setInt(3, i%2);
            ps.addBatch();
        }
        final int[] ints = ps.executeBatch();
        connection.commit();
        ps.close();
        connection.close();
        return ints.length;
    }
}
