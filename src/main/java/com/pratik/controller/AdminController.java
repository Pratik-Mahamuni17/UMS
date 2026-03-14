package com.pratik.controller;

import com.pratik.constant.Constant;
import com.pratik.constant.dto.request.UserRequestDto;
import com.pratik.constant.dto.response.Response;
import com.pratik.constant.enums.RoleType;
import com.pratik.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.BASE)
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Constant.CREATE_USER)
    public ResponseEntity<Response> createUser(@RequestBody UserRequestDto request) {

        Response response = userService.createUser(request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }



    @GetMapping(Constant.GET_ALL_USERS)
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping(Constant.UPDATE_USER )
    public ResponseEntity<Response> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto request) {

        Response response = userService.updateUser(id, request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping(Constant.DELETE_USER)
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {

        Response response = userService.deleteUser(id);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping(Constant.ASSIGN_ROLE)
    public ResponseEntity<Response> assignRole(
            @PathVariable Long id,
            @RequestParam RoleType roleType) {

        Response response = userService.assignRole(id, roleType);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}