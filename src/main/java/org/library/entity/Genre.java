package org.library.entity;

import java.util.Objects;

public class Genre {
    private final String title;
    private int id;

    public Genre(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
                title.equals(genre.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
}
