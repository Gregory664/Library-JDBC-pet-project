package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookshelf {
    private int id;
    private Shelf shelf;
    private BookCopy bookCopy;
}
