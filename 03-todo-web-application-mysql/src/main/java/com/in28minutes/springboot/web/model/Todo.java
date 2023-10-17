package com.in28minutes.springboot.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

  @Id @GeneratedValue private int id;
  private String userName;
  //@Size(min = 10, message = "Enter at least 10 Characters...")
  private String description;
  private Date targetDate;
  private boolean isDone;
}