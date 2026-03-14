package com.pratik.service.impl;


import com.pratik.constant.Constant;
import com.pratik.constant.dto.request.TaskRequestDto;
import com.pratik.constant.dto.response.ErrorResponseDto;
import com.pratik.constant.dto.response.Response;
import com.pratik.constant.dto.response.TaskResponseDto;
import com.pratik.constant.dto.response.UserTaskResponseDto;
import com.pratik.entities.Task;
import com.pratik.entities.User;
import com.pratik.repository.TaskRepository;
import com.pratik.repository.UserRepository;
import com.pratik.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository  = taskRepository;

    }

    @Override
    public Response assignTask(Long userId, TaskRequestDto request) {

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return new Response(
                    "User not found",
                    new ErrorResponseDto("USER_NOT_FOUND", "User does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            return new Response(
                    "Invalid request",
                    new ErrorResponseDto("TASK_TITLE_REQUIRED", "Task title is required"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return new Response(
                Constant.SUCCESS,
                HttpStatus.CREATED
        );
    }

    @Override
    public Response getUsersWithTasks() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return new Response(
                    "No users found",
                    new ErrorResponseDto("USERS_NOT_FOUND", "No users exist in the system"),
                    HttpStatus.NOT_FOUND
            );
        }

        List<UserTaskResponseDto> responseList = users.stream().map(user -> {

            UserTaskResponseDto dto = new UserTaskResponseDto();
            dto.setUserId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());

            List<TaskResponseDto> tasks = taskRepository.findByUserId(user.getId())
                    .stream()
                    .map(task -> {
                        TaskResponseDto taskDto = new TaskResponseDto();
                        taskDto.setId(task.getId());
                        taskDto.setTitle(task.getTitle());
                        taskDto.setDescription(task.getDescription());
                        return taskDto;
                    })
                    .toList();

            dto.setTasks(tasks);

            return dto;

        }).toList();

        return new Response(
                Constant.SUCCESS,
                responseList,
                HttpStatus.OK
        );
    }
}
