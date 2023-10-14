package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.AssessmentDTO;
import com.bcaf.tugasakhir.dto.IdDTO;
import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.service.AssessmentService;
import com.bcaf.tugasakhir.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ResponseEntity<Object> findAll(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return userService.findById(id, request);
    }
}
