package com.pratik.constant.dto.request;


import com.pratik.constant.enums.RoleType;
import lombok.Data;

@Data
public class UserRequestDto {


    private String name;

    private String email;


    private String password;

}