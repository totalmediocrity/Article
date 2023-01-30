package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Entity
public class Comment {
    public Comment(String header, String tc, char app) {
        this.header = header;
        this.tc = tc;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"

        this.dOfp = formattedDateTime;
        this.app = app;
    }

    public Comment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 200, message = "Поле должно иметь меньше 200 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "[a-zA-Zа-яА-Я]{1,200}", message = "Только кирилица, от 1-200 символов")
    private String header, tc;
    private String dOfp;
    private char app;

    @ManyToMany
    @JoinTable(name="user_comment",
            joinColumns=@JoinColumn(name="comment_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }


    public String getdOfp() {
        return dOfp;
    }

    public void setdOfp(String dOfp) {
        this.dOfp = dOfp;
    }

    public char getApp() {
        return app;
    }

    public void setApp(char app) {
        this.app = app;
    }


}
