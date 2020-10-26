package org.library.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class BookRent {
    private int id;
    private Reader reader;
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookRent(Reader reader, Book book, LocalDate startDate, LocalDate endDate) {
        this.reader = reader;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRent bookRent = (BookRent) o;
        return id == bookRent.id &&
                reader.equals(bookRent.reader) &&
                book.equals(bookRent.book) &&
                startDate.equals(bookRent.startDate) &&
                endDate.equals(bookRent.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reader, book, startDate, endDate);
    }
}