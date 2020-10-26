package org.library.entity;

import java.util.Objects;

public class Publisher {
    private final int id;
    private final String title;

    public Publisher(int id, String title) {
        this.id = id;
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
        Publisher publisher = (Publisher) o;
        return id == publisher.id &&
                title.equals(publisher.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
