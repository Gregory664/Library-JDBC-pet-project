package org.library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String driver = "jdbc:mysql:";
    private static final String params = "useUnicode=True&serverTimezone=UTC";
    private static String dbUserName;
    private static String dbPassword;
    private static String url;

    public static void init(String host, String port, String dbName, String userName, String password) {
        dbUserName = userName;
        dbPassword = password;
        url = String.format("%s//%s:%s/%s?%s", driver, host, port, dbName, params);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUserName, dbPassword);
    }
}
