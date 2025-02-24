package com.skbit.techrel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.entity.Task;
import com.skbit.techrel.service.TaskService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	
	 @PostMapping("/create")
	    public ResponseEntity<Task> create(@RequestBody Task task) {
	        Task createdTask = taskService.create(task);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
	    }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<Task> update(@PathVariable("id") Long id, @RequestBody Task task) {
	     task.setId(id); 
	     Task updatedTask = taskService.update(task);
	     return ResponseEntity.ok(updatedTask);
	 }


	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
	        taskService.delete(id);
	        return new ResponseEntity<ApiResponse>(taskService.delete(id), HttpStatus.OK);  
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Task> findById(@PathVariable("id") Long id) {
	        Task task = taskService.findById(id);
	        return ResponseEntity.ok(task);
	    }


	    @GetMapping("/all")
	    public ResponseEntity<List<Task>> findAll() {
	        List<Task> tasks = taskService.findAll();
	        return ResponseEntity.ok(tasks);
	    }
	 
	 
	
	
}
