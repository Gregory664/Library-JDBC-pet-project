package org.library.entity;

import java.util.Objects;

public class Bookshelf {
    private int id;
    private Shelf shelf;
    private Book book;

    public Bookshelf(Shelf shelf, Book book) {
        this.shelf = shelf;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookshelf bookshelf = (Bookshelf) o;
        return id == bookshelf.id &&
                shelf.equals(bookshelf.shelf) &&
                book.equals(bookshelf.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shelf, book);
    }
}
