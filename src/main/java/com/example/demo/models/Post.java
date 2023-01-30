package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class Post {
    public Post(String title,String anons, String full_text, User user) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.user = user;
    }

    public Post() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotEmpty(message = "Поле не может быть пустым")
//    @Size(message = "Размер данного поля должен быть в диапозоне от 2 до 50")
    @Size(max = 200, message = "Поле должно иметь меньше 200 символов")
    @NotBlank(message = "Поле не должно быть пустым")
//    @Pattern(regexp = "[a-zA-Zа-яА-Я]{1,200}", message = "Только кирилица, от 1-200 символов")
    private String title, anons, full_text;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public User user;

//    public Post(String title, String anons, User user, String full_text) {
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
