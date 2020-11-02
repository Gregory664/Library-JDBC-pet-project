package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelf implements Comparable<Shelf> {
    private int id;
    private String inventNum;

    @Override
    public int compareTo(Shelf shelf) {
        return Integer.compare(this.id, shelf.getId());
    }
}
