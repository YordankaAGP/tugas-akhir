package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private ModelMapper modelMapper;

    private UserService userService;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return userService.findById(id, request);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return userService.findAll(request);
    }
}
