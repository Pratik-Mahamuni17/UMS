package com.pratik.service;

import com.pratik.constant.dto.request.TaskRequestDto;
import com.pratik.constant.dto.response.Response;

public interface TaskService {

    Response assignTask(Long userId, TaskRequestDto request);

    Response getUsersWithTasks();


}
