package com.pratik.constant;

public class Constant {
    public static final String SUCCESS = "Success";

    public static final String BASE = "/admin";
    public static final String CREATE_USER = "";              // POST    /users
    public static final String GET_ALL_USERS = "";            // GET     /users     // GET     /users/{id}
    public static final String UPDATE_USER = "/{id}";         // PUT     /users/{id}
    public static final String DELETE_USER = "/{id}";         // DELETE  /users/{id}

    public static final String ASSIGN_ROLE = "/{id}/roles";   // POST    /users/{id}/roles
    public static final String User_BASE_URL = "/user";
    public static final String GetMyProfile = "/me";

    public static final String MANAGER_BASE_URL = "/manager";
    public static final String ASSIGN_TASK = "/users/{id}/tasks";
    public static final String GetUsersWithTasks = "/users/tasks";
}
