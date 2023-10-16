package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.*;
import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.service.AssessmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    private ModelMapper modelMapper;

    private AssessmentService assessmentService;


    @Autowired
    public AssessmentController(ModelMapper modelMapper, AssessmentService assessmentService) {
        this.modelMapper = modelMapper;
        this.assessmentService = assessmentService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody PostAssessmentDTO postAssessmentDTO, HttpServletRequest request)
    {
        Assessment assessment = modelMapper.map(postAssessmentDTO, new TypeToken<Assessment>() {}.getType());
        return assessmentService.save(assessment,request);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return assessmentService.findAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return assessmentService.findById(id, request);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findByUserId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return assessmentService.findByUserId(id, request);
    }

    @GetMapping("/user/{id}/incomplete")
    public ResponseEntity<Object> findIncompleteByUserId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return assessmentService.findIncompleteByUserId(id, request);
    }

    @GetMapping("/user/{id}/complete")
    public ResponseEntity<Object> findCompleteByUserId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return assessmentService.findCompleteByUserId(id, request);
    }


    @PutMapping("/{id}/participant")
    public ResponseEntity<Object> addParticipant(@PathVariable(value = "id") Long id, @Valid @RequestBody IdDTO idDTO, HttpServletRequest request)
            throws Exception
    {
        return assessmentService.addParticipant(id,idDTO,request);
    }

    @PostMapping("/{id}/question")
    public ResponseEntity<Object> addQuestion(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AddQuestionDTO addQuestionDTO,
            HttpServletRequest request
    ) throws Exception {
        return assessmentService.addQuestion(id, addQuestionDTO, request);
    }

    @PostMapping("/{id}/result")
    public ResponseEntity<Object> addResult(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ResultByAssessmentDTO resultByAssessmentDTO,
            HttpServletRequest request
    ) throws Exception {
        return assessmentService.addResult(id, resultByAssessmentDTO, request);
    }



//    @PutMapping("/v1/update/{id}")
//    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody AssessmentDTO assessmentDTO, HttpServletRequest request)
//            throws Exception
//    {
//        Assessment assessment = modelMapper.map(assessmentDTO, new TypeToken<Assessment>() {}.getType());
//        return assessmentService.update(id,assessment,request);
//    }

}
