package org.library.entity;

import java.util.Objects;

public class Genre {
    private String title;
    private int id;

    public Genre(String title) {
        this.title = title;
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
