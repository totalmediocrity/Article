package com.example.demo.repo;

import com.example.demo.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository <Comment, Long> {
}
