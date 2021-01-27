package org.library.exceptions;

import org.library.utils.MessageBox;

import java.sql.SQLException;

public class SQLExceptionWrapper extends RuntimeException {
    public SQLExceptionWrapper(SQLException e) {
        String warningText = String.format("" +
                "Ошибка обработки запроса: \n " +
                "Error code: %s \n " +
                "State: %s \n " +
                "Message: %s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        System.out.println(warningText);
        MessageBox.WarningBox(warningText).show();
    }
}
