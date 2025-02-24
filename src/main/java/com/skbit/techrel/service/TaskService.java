package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Task;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.TaskRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public Task create(Task task) {
		return taskRepository.save(task);
	}
	
	public Task update(Task task) {
		Task createdtask = taskRepository.findById(task.getId())
			 .orElseThrow(()-> new NotFoundException("Task not found"));
		createdtask.setTaskName(task.getTaskName());
		createdtask.setDescription(task.getDescription());
		createdtask.setDate(task.getDate());
		
		return taskRepository.save(createdtask);
		
	}
	
	public ApiResponse delete(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(()->new NotFoundException("task not found with this id: "+id));
		return ApiResponse.builder().message("task deleted successfully").status(true).build();
	}
	
	public Task findById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("task not found with ID:"+id));
		
	}
	
	public List<Task> findAll(){
		return taskRepository.findAll();
	}
}
