package com.example.demo.repo;

import com.example.demo.models.Comment;
import com.example.demo.models.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository <Comment, Long> {
    List<Comment> findByHeader(String header);
    List<Comment> findByHeaderContains(String header);

    Comment findByTc (String tc);
}

