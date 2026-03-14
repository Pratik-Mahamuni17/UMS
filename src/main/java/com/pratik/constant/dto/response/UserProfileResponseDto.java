package com.pratik.constant.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserProfileResponseDto {

    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
    private List<TaskResponseDto> tasks;

}