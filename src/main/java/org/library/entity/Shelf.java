package org.library.entity;

import java.util.Objects;

public class Shelf {
    private int id;
    private String inventNum;

    public Shelf(int id, String inventNum) {
        this.id = id;
        this.inventNum = inventNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventNum() {
        return inventNum;
    }

    public void setInventNum(String inventNum) {
        this.inventNum = inventNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return id == shelf.id &&
                inventNum.equals(shelf.inventNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventNum);
    }
}
