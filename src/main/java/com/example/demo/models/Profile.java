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

    public Profile(String nik, String name, String yourself, char pol, int age, Date dOfs) {
        this.nik = nik;
        this.name = name;
        this.yourself = yourself;
        this.pol = pol;
        this.age = age;
        this.dOfs = dOfs;
    }

    public Profile() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    @Size(message = "Размер данного поля должен быть в диапозоне от 2 до 50")
    private String nik, name,yourself;
    private char pol;
    @Min(value = 18, message = "Пользователь должен быть старше 18")
    @NotNull(message = "Поле не может быть пустым")
    @Positive(message = "Поле должно быть больше 0")
    private int age;
    private Date dOfs;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getdOfs() {
        return dOfs;
    }

    public void setdOfs(Date dOfs) {
        this.dOfs = dOfs;
    }
}
