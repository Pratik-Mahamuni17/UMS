package com.pratik.service.impl;

import com.pratik.constant.Constant;
import com.pratik.constant.dto.request.UserRequestDto;
import com.pratik.constant.dto.response.*;
import com.pratik.constant.enums.RoleType;
import com.pratik.entities.Role;
import com.pratik.entities.User;
import com.pratik.repository.RoleRepository;
import com.pratik.repository.TaskRepository;
import com.pratik.repository.UserRepository;
import com.pratik.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           TaskRepository taskRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response createUser(UserRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new Response(
                    "User already exists",
                    new ErrorResponseDto("USER_ALREADY_EXISTS", "User with this email already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Role role = roleRepository.findByName(RoleType.USER).orElse(null);

        if (role == null) {
            return new Response(
                    "Role not found",
                    new ErrorResponseDto("ROLE_NOT_FOUND", "Default role USER not found"),
                    HttpStatus.NOT_FOUND
            );
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return new Response(
                Constant.SUCCESS,
                HttpStatus.CREATED
        );
    }

    @Override
    public Response getAllUsers() {

        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setRoles(
                            user.getRoles()
                                    .stream()
                                    .map(role -> role.getName().name())
                                    .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .toList();

        return new Response(
                Constant.SUCCESS,
                users,
                HttpStatus.OK
        );
    }

    @Override
    public Response updateUser(Long id, UserRequestDto request) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return new Response(
                    "User not found",
                    new ErrorResponseDto("USER_NOT_FOUND", "User does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return new Response(
                Constant.SUCCESS,
                HttpStatus.OK
        );
    }

    @Override
    public Response deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return new Response(
                    "User not found",
                    new ErrorResponseDto("USER_NOT_FOUND", "User does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        userRepository.delete(user);

        return new Response(
                Constant.SUCCESS,
                HttpStatus.OK
        );
    }

    @Override
    public Response assignRole(Long userId, RoleType roleType) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return new Response(
                    "User not found",
                    new ErrorResponseDto("USER_NOT_FOUND", "User does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        Role role = roleRepository.findByName(roleType).orElse(null);

        if (role == null) {
            return new Response(
                    "Role not found",
                    new ErrorResponseDto("ROLE_NOT_FOUND", "Role does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        user.getRoles().add(role);
        userRepository.save(user);

        return new Response(
                Constant.SUCCESS,
                HttpStatus.OK
        );
    }

    @Override
    public Response getUserProfileWithTasks(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return new Response(
                    "User not found",
                    new ErrorResponseDto("USER_NOT_FOUND", "User does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }

        UserProfileResponseDto dto = new UserProfileResponseDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoles(
                user.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );

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

        return new Response(
                Constant.SUCCESS,
                dto,
                HttpStatus.OK
        );
    }}