package org.library.utils.statements;

public class BookSQLStatements {
    public static final String FIND_ALL = "" +
            "SELECT b.id, b.title, b.length, " +
            "a.id as 'author_id', a.name as 'author_name', " +
            "p.id as 'publisher_id', p.title as 'publisher_title', " +
            "g.id as 'genre_id', g.title as 'genre_title' " +
            "FROM  book b " +
            "JOIN  author a    ON b.author_id    = a.id " +
            "JOIN  publisher p ON b.publisher_id = p.id " +
            "JOIN  genre g     ON b.genre_id     = g.id ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE b.id = ?";

    public static final String FIND_BY_AUTHOR = FIND_ALL + "WHERE a.id = ?";

    public static final String FIND_BY_PUBLISHER = FIND_ALL + "WHERE p.id = ?";

    public static final String FIND_BY_GENRE = " WHERE g.id = ?";

    public static final String FIND_BY_TITLE = "WHERE b.title = ?";


    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM book WHERE id = ?;";

    public static final String DELETE_ALL = "DELETE FROM book;";

    public static final String DELETE_BY_ID = "DELETE FROM book WHERE id = ?;";

    public static final String SAVE = "" +
            "INSERT INTO book (title, author_id, publisher_id, genre_id, length) " +
            "VALUES (?, ?, ?, ?, ?);";

    public static final String COUNT = "SELECT COUNT(*) FROM book;";

    public static final String UPDATE = "" +
            "UPDATE book " +
            "SET    title        = ?, " +
            "       author_id    = ?, " +
            "       publisher_id = ?, " +
            "       genre_id     = ?, " +
            "       length       = ? " +
            "WHERE  id = ?";
}
