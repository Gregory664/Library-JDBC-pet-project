package org.library.utils.statements;

public class StatisticsStatements {
    public static final String COUNT_BY_AUTHOR_NAME_IN_SHELF = "" +
            "SELECT a.name, COUNT(a.name) " +
            "FROM book_copy bc " +
            "INNER JOIN book b ON bc.book_id = b.id " +
            "INNER JOIN author a ON b.author_id = a.id " +
            "INNER JOIN bookshelf bs ON bs.bookCopy_id = bc.id " +
            "GROUP BY a.name;";

    public static final String COUNT_BY_AUTHOR_NAME_IN_RENT = "" +
            "SELECT a.name, COUNT(a.name) " +
            "FROM book_copy bc " +
            "INNER JOIN book b ON bc.book_id = b.id " +
            "INNER JOIN author a ON b.author_id = a.id " +
            "INNER JOIN book_rent br ON br.bookCopy_id = bc.id " +
            "GROUP BY a.name;";

    public static final String COUNT_BY_GENRE_TITLE_IN_SHELF = "" +
            "SELECT g.title, COUNT(g.title) " +
            "FROM book_copy bc " +
            "INNER JOIN book b ON bc.book_id = b.id " +
            "INNER JOIN genre g ON b.genre_id = g.id " +
            "INNER JOIN bookshelf bs ON bs.bookCopy_id = bc.id " +
            "GROUP BY g.title;";

    public static final String COUNT_BY_GENRE_TITLE_IN_RENT = "" +
            "SELECT g.title, COUNT(g.title) " +
            "FROM book_copy bc " +
            "INNER JOIN book b ON bc.book_id = b.id " +
            "INNER JOIN genre g ON b.genre_id = g.id " +
            "INNER JOIN book_rent br ON br.bookCopy_id = bc.id " +
            "GROUP BY g.title;";
}
