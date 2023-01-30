package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Email(regexp = "[a-zA-Z0-9]{3,20}@[a-zA-Z0-9]{3,15}[.][a-zA-Z]{2,5}", message= "Некорретный ввод электронной почты")
    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять из одних пробелов")
    private String email;
    @Column(unique = true)
    private String phoneNumber;

    @OneToOne(optional = true, mappedBy = "contacts")
    private User user;

    public Contacts(String email, String phoneNumber, User user) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public Contacts() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
