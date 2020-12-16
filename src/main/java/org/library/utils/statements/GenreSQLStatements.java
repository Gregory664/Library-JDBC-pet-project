package org.library.utils.statements;

public class GenreSQLStatements {
    public static final String FIND_ALL = "SELECT * FROM genre ";

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?;";

    public static final String FIND_BY_TITLE = FIND_ALL + " WHERE title = ?;";

    public static final String COUNT = "SELECT COUNT(*) FROM genre ";

    public static final String EXISTS_BY_ID = COUNT + " WHERE id = ?;";

    public static final String DELETE = "DELETE FROM genre ";

    public static final String DELETE_BY_ID = DELETE + "WHERE id = ?;";

    public static final String SAVE = "INSERT INTO genre (title) VALUES (?);";

    public static final String UPDATE = "" +
            "UPDATE genre " +
            "SET    title = ? " +
            "WHERE  id    = ?";
}
