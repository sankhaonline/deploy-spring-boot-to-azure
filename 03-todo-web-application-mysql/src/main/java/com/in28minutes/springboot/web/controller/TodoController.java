package com.in28minutes.springboot.web.controller;

import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.service.TodoRepository;
import jakarta.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TodoController {

  private final TodoRepository repository;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Date - dd/MM/yyyy
    var dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
  public String showTodos(ModelMap model) {
    var name = getLoggedInUserName(model);
    model.put("todos", repository.findByUserName(name));
    // model.put("todos", service.retrieveTodos(name));
    return "list-todos";
  }

  private String getLoggedInUserName(ModelMap model) {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }

    return principal.toString();
  }

  @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
  public String showAddTodoPage(ModelMap model) {
    model.addAttribute(
        "todo", new Todo(0, getLoggedInUserName(model), "Default Desc", new Date(), false));
    return "todo";
  }

  @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
  public String deleteTodo(@RequestParam int id) {

    // if(id==1)
    // throw new RuntimeException("Something went wrong");
    repository.deleteById(id);
    // service.deleteTodo(id);
    return "redirect:/list-todos";
  }

  @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
  public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
    var todo = repository.findById(id).get();
    // Todo todo = service.retrieveTodo(id);
    model.put("todo", todo);
    return "todo";
  }

  @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
  public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

    if (result.hasErrors()) {
      return "todo";
    }

    todo.setUserName(getLoggedInUserName(model));

    repository.save(todo);
    // service.updateTodo(todo);

    return "redirect:/list-todos";
  }

  @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
  public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

    if (result.hasErrors()) {
      return "todo";
    }

    todo.setUserName(getLoggedInUserName(model));
    repository.save(todo);
    /*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
    false);*/
    return "redirect:/list-todos";
  }
}
