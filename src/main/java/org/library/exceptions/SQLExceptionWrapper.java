package org.library.exceptions;

import java.sql.SQLException;

public class SQLExceptionWrapper extends RuntimeException {
    public SQLExceptionWrapper(SQLException e) {
        System.err.println("Error code: " + e.getErrorCode());
        System.err.println("State: " + e.getSQLState());
        System.err.println("Massage: " + e.getMessage());
        e.printStackTrace();
    }
}
