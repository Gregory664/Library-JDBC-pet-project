package org.library.utils.statements;

public class AuthorSQLStatements {
    public static final String FIND_ALL = "SELECT * FROM author ";

    public static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?;";

    public static final String COUNT = "SELECT COUNT(*) FROM author ";

    public static final String EXISTS_BY_ID = COUNT + " WHERE id = ?;";

    public static final String DELETE = "DELETE FROM author ";

    public static final String DELETE_BY_ID = DELETE + "WHERE id = ?;";

    public static final String SAVE = "INSERT INTO author (name) VALUES (?);";
}
