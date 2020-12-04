package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCopy implements Comparable<BookCopy> {
    private int id;
    private Book book;

    public BookCopy(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return Objects.equals(book, bookCopy.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book);
    }

    @Override
    public int compareTo(BookCopy bookCopy) {
        return Integer.compare(id, bookCopy.getId());
    }
}
