package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Comparable<Book> {
    private int id;
    private String title;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private int length;
    private Map<Integer, Shelf> bookCopyIdAndShelf = new TreeMap<>();

    public Book(String title, Author author, Publisher publisher, Genre genre, int length) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.length = length;
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(this.id, book.id);
    }
}
