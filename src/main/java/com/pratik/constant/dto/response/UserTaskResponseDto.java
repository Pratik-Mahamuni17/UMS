package com.pratik.constant.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class UserTaskResponseDto {

    private Long userId;
    private String name;
    private String email;
    private List<TaskResponseDto> tasks;

}