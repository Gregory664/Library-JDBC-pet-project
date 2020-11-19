package org.library.utils.statements;

public class BookCopySQLStatements {
    public static final String FIND_ALL = "SELECT id, book_id FROM book_copy ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE id = ?";

    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM book_copy WHERE id = ?;";

    public static final String DELETE_ALL = "DELETE FROM book_copy;";

    public static final String DELETE_BY_ID = "DELETE FROM book_copy WHERE id = ?;";

    public static final String SAVE = "INSERT INTO book_copy (book_id) VALUES (?);";

    public static final String COUNT = "SELECT COUNT(*) FROM book_copy;";
}
