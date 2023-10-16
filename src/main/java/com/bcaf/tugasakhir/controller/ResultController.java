package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/result")
public class ResultController {
    private ModelMapper modelMapper;

    private ResultService resultService;


    @Autowired
    public ResultController(ResultService resultService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.resultService = resultService;
    }

    @GetMapping("/assessment/{id}")
    public ResponseEntity<Object> findByAssessmentId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return resultService.findByAssessmentId(id, request);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findByUserId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return resultService.findByUserId(id, request);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return resultService.findAll(request);
    }
}