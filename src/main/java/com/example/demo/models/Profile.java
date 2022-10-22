package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;


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
  /*  @NotEmpty(message = "Поле не может быть пустым")
    @Size(message = "Размер данного поля должен быть в диапозоне от 2 до 50")*/
    private String nik, name,yourself;
    private char pol;
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
