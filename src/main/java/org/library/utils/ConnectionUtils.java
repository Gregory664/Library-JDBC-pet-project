package org.library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getConnection() throws SQLException {
        final String URL = "jdbc:mysql://localhost:3306/library?useUnicode=True&serverTimezone=UTC";
        final String userName = "greg";
        final String password = "greg2704";

        return DriverManager.getConnection(URL, userName, password);
    }
}
