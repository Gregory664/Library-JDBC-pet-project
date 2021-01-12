package org.library.utils.statements;

public class ShelfSQLStatements {
    public static final String FIND_ALL = "SELECT * FROM shelf ";

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?;";

    public static final String FIND_BY_INVENT_NUM = FIND_ALL + " WHERE invent_num = ?;";

    public static final String COUNT = "SELECT COUNT(*) FROM shelf ";

    public static final String EXISTS_BY_ID = COUNT + " WHERE id = ?;";

    public static final String DELETE = "DELETE FROM shelf ";

    public static final String DELETE_BY_ID = DELETE + "WHERE id = ?;";

    public static final String SAVE = "INSERT INTO shelf (invent_num) VALUES (?);";

    public static final String UPDATE = "" +
            "UPDATE shelf " +
            "SET    invent_num = ? " +
            "WHERE  id         = ?";
}
