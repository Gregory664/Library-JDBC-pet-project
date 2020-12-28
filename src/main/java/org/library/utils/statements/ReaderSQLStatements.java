package org.library.utils.statements;

public class ReaderSQLStatements {
    public static final String COUNT = "SELECT COUNT(*) FROM reader ";

    public static final String EXISTS_BY_PASSPORT = COUNT + "WHERE passport = ?;";

    public static final String EXISTS_BY_ID = COUNT + "WHERE id = ?;";

    public static final String FIND_ALL = "SELECT * FROM reader ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE id = ?;";

    public static final String FIND_BY_FIO_LIKE = FIND_ALL + "WHERE fio LIKE ?;";

    public static final String DELETE_ALL = "DELETE FROM reader ";

    public static final String DELETE_BY_ID = DELETE_ALL + "WHERE id = ?;";

    public static final String SAVE = "" +
            "INSERT INTO reader (fio, age, address, phone, passport, gender, DOB) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE = "" +
            "UPDATE reader " +
            "SET    fio      = ?, " +
            "       age      = ?, " +
            "       address  = ?, " +
            "       phone    = ?, " +
            "       passport = ?, " +
            "       gender   = ?, " +
            "       DOB      = ?  " +
            "WHERE  id       = ?";
}
