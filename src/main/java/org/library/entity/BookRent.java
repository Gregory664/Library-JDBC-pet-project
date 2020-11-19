package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRent {
    private int id;
    private Reader reader;
    private BookCopy bookCopy;
    private LocalDate startDate;
    private LocalDate endDate;
}
