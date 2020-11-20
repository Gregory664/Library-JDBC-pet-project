package org.library.utils.statements;

public class PublisherSQLStatements {
    public static final String FIND_ALL = "SELECT * FROM publisher ";

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?;";

    public static final String COUNT = "SELECT COUNT(*) FROM publisher ";

    public static final String EXISTS_BY_ID = COUNT + " WHERE id = ?;";

    public static final String DELETE = "DELETE FROM publisher ";

    public static final String DELETE_BY_ID = DELETE + "WHERE id = ?;";

    public static final String SAVE = "INSERT INTO publisher (title) VALUES (?);";
}
