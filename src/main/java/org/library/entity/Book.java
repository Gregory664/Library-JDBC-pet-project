package org.library.entity;

import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private int length;

    public Book(String title, Author author, Publisher publisher, Genre genre, int length) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                length == book.length &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                publisher.equals(book.publisher) &&
                genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher, genre, length);
    }
}
