package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Comparable<Book> {
    private int id;
    private String title;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private int length;
    private Map<Integer, Shelf> bookCopyIdAndShelf = new TreeMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                length == book.length &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(bookCopyIdAndShelf, book.bookCopyIdAndShelf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher, genre, length, bookCopyIdAndShelf);
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(this.id, book.id);
    }
}
