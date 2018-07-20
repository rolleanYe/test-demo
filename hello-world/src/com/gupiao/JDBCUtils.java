package com.gupiao;

import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import java.sql.Statement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private void JDBCUtils() {};
    private  static String sqlurl = "";
    private  static String sqluser = "";
    private  static String sqlpwd = "";
    private  static String classforName = "";
    private static  Connection con = null;

    static {
        try {
            initConfig();
            Class.forName(classforName);
            con = (Connection) DriverManager.getConnection(sqlurl, sqluser, sqlpwd);
        } catch (ClassNotFoundException e) {
            System.out.println("数据驱动失败");
            e.printStackTrace();
        } catch (SQLException e) {

            System.out.println("数据连接失败");
            e.printStackTrace();
        }
    }

    private static void initConfig() {
        InputStream inf = JDBCUtils.class.getClassLoader().getResourceAsStream("database.propertties");
        Properties properties = new Properties();
        try {
            properties.load(inf);
            classforName = properties.getProperty("driverClass");
            sqlurl = properties.getProperty("url");
            sqluser = properties.getProperty("username");
            sqlpwd = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println("读取配置文件失败");
        }
    }

    public static Connection getConnection() {
        return con;
    }
    public static void close(Connection con,Statement preparedStatement,ResultSet rs)   {
        if(con !=null) {
            try {
                con.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }

        }
        if (preparedStatement!=null) {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        if (rs!=null) {
            try {
                rs.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}