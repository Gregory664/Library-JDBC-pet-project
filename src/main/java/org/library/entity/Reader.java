package org.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    private int id;
    private String fio;
    private int age;
    private String address;
    private String phone;
    private String passport;
    private Map<Book, Period> rentBooks = new TreeMap<>();
}
