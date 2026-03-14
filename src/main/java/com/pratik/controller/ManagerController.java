package com.pratik.controller;

import com.pratik.constant.Constant;
import com.pratik.constant.dto.request.TaskRequestDto;
import com.pratik.constant.dto.response.Response;
import com.pratik.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.MANAGER_BASE_URL)
public class ManagerController {

    private final TaskService taskService;

    public ManagerController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(Constant.ASSIGN_TASK)
    public ResponseEntity<Response> assignTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDto request) {

        Response response = taskService.assignTask(id, request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(Constant.GetUsersWithTasks)
    public ResponseEntity<Response> getUsersWithTasks() {

        Response response = taskService.getUsersWithTasks();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}