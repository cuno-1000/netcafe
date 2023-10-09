package com.netcafe.admin.db;

import com.mysql.cj.jdbc.StatementImpl;
import com.netcafe.admin.page.UserInfoPage;

import javax.swing.*;
import java.sql.*;

public class DbConn {
    //驱动类的类名
    private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver"; // 邓竣中
    //连接数据的URL路径
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?useSSL=true&serverTimezone=GMT";
    //数据库登录账号
    private static final String USER = "root";
    //数据库登录密码
    private static final String PASSWORD = "Cuno_u1000";

    //加载驱动
    static {
        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询
    public static ResultSet query(String sql) {
//        System.out.println(sql);
        //获取连接
        Connection connection = getConnection();
        PreparedStatement psd;
        try {
            psd = connection.prepareStatement(sql);
            return psd.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "执行语句出错\n" + e);
            e.printStackTrace();
        }
        return null;
    }

    //增、删、改、查
    public static int updataInfo(String sql) {
//        System.out.println(sql);
        //获取连接
        Connection connection = getConnection();
        try {
            PreparedStatement psd = connection.prepareStatement(sql);
            return psd.executeUpdate();
        } catch (SQLException e) {
            UserInfoPage.showSqlErrorWindow(e);
//            e.printStackTrace();
            return -1;
        }
    }

    //关闭连接
    public static void close(ResultSet rs, StatementImpl stmt, Connection conn) throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.cancel();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
