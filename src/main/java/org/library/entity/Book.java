package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private int length;
    private Map<Shelf, Integer> countOfBookInShelf = new TreeMap<>();
}
