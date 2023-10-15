package com.in28minutes.springboot.web.service;

import com.in28minutes.springboot.web.model.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
  List<Todo> findByUserName(String user);
}
