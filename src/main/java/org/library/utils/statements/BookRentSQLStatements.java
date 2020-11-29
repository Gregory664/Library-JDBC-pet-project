package org.library.utils.statements;

public class BookRentSQLStatements {
    public static final String GET_RENT_BOOK_COPY_BY_READER_ID = "" +
            "SELECT bookCopy_id, start_date, end_date " +
            "FROM   book_rent " +
            "WHERE  reader_id = ?;";

    public static final String DELETE_RENT_BOOK_COPY = "" +
            "DELETE FROM book_rent " +
            "WHERE reader_id = ? AND bookCopy_id = ?;";

    public static final String ADD_RENT_BOOK_COPY_TO_READER = "" +
            "INSERT INTO book_rent (reader_id, bookCopy_id, start_date, end_date) " +
            "VALUES(?, ?, ?, ?)";
}
