package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Comment {
    public Comment(String header, String tc, int writer, Date dOfp, char app) {
        this.header = header;
        this.tc = tc;
        this.writer = writer;
        this.dOfp = dOfp;
        this.app = app;
    }

    public Comment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header, tc;
    private int writer;
    private Date dOfp;
    private char app;

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

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public Date getdOfp() {
        return dOfp;
    }

    public void setdOfp(Date dOfp) {
        this.dOfp = dOfp;
    }

    public char getApp() {
        return app;
    }

    public void setApp(char app) {
        this.app = app;
    }
}
