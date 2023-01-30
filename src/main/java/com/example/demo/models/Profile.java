package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.Collection;
import java.util.List;


@Entity
public class Profile {

    public Profile(String nik, String name, String surname,String patron, String yourself, char pol, Date age) {
        this.nik = nik;
        this.name = name;
        this.surname = surname;
        this.patron = patron;
        this.yourself = yourself;
        this.pol = pol;
        this.age = age;
    }

    public Profile() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    @Size(message = "Размер данного поля должен быть в диапозоне от 2 до 50")
    private String nik, name,surname,patron,yourself;
    private char pol;
    @Past(message = "Дата рождения не должна быть в будущем")
    private Date age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYourself() {
        return yourself;
    }

    public void setYourself(String yourself) {
        this.yourself = yourself;
    }

    public char getPol() {
        return pol;
    }

    public void setPol(char pol) {
        this.pol = pol;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }
}
