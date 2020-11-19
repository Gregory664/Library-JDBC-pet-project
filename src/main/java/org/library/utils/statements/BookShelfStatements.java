package org.library.utils.statements;

public class BookShelfStatements {
    public static final String BOOK_COPY_AND_SHELF = "" +
            "SELECT bh.bookCopy_id, s.id, s.invent_num \n" +
            "FROM   bookshelf bh \n" +
            "JOIN   shelf s ON bh.shelf_id = s.id \n" +
            "JOIN   book_copy bc  ON bh.bookCopy_id = bc.id \n" +
            "WHERE  bc.book_id = ?;";

    public static final String DELETE_FROM_BOOK_SHELF = "" +
            "DELETE from bookshelf " +
            "WHERE shelf_id = ? AND bookCopy_id = ? LIMIT 1;";

    public static final String ADD_BOOK_COPY_TO_SHELF = "" +
            "INSERT INTO bookshelf (bookCopy_id, shelf_id) " +
            "VALUES (?, ?);";
}
