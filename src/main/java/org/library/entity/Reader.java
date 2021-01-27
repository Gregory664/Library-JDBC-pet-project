package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.library.utils.Gender;

import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader {
    private int id;
    private String fio;
    private int age;
    private String address;
    private String phone;
    private String passport;
    private Gender gender;
    private Date DOB;
    private Map<BookCopy, Period> rentBookCopies = new TreeMap<>();
}
