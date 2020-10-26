package org.library.entity;

import java.util.Objects;

public class Reader {
    private int id;
    private String fio;
    private int age;
    private String address;
    private String phone;
    private String passport;

    public Reader(String fio, int age, String address, String phone, String passport) {
        this.fio = fio;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.passport = passport;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id &&
                age == reader.age &&
                fio.equals(reader.fio) &&
                address.equals(reader.address) &&
                phone.equals(reader.phone) &&
                passport.equals(reader.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passport);
    }
}
