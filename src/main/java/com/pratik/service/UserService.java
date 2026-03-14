package com.pratik.service;

import com.pratik.constant.dto.request.UserRequestDto;
import com.pratik.constant.dto.response.Response;
import com.pratik.constant.dto.response.UserResponseDto;
import com.pratik.constant.enums.RoleType;

import java.util.List;

public interface UserService {

    Response createUser(UserRequestDto request);
    Response getAllUsers();
    Response updateUser(Long id, UserRequestDto request);
    Response deleteUser(Long id);

    Response assignRole(Long userId, RoleType roleType);
    Response getUserProfileWithTasks(String email);

}
