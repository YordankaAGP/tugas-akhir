package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.AssessmentDTO;
import com.bcaf.tugasakhir.dto.IdDTO;
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
    public ResponseEntity<Object> save(@Valid @RequestBody AssessmentDTO assessmentDTO, HttpServletRequest request)
    {
        Assessment assessment = modelMapper.map(assessmentDTO, new TypeToken<Assessment>() {}.getType());
        return assessmentService.save(assessment,request);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return assessmentService.findAll(request);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findByUserId(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return assessmentService.findByUserId(id, request);
    }


    @PutMapping("/{id}/participant")
    public ResponseEntity<Object> addParticipant(@PathVariable(value = "id") Long id, @RequestBody IdDTO idDTO, HttpServletRequest request)
            throws Exception
    {
        return assessmentService.addParticipant(id,idDTO,request);
    }



//    @PutMapping("/v1/update/{id}")
//    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody AssessmentDTO assessmentDTO, HttpServletRequest request)
//            throws Exception
//    {
//        Assessment assessment = modelMapper.map(assessmentDTO, new TypeToken<Assessment>() {}.getType());
//        return assessmentService.update(id,assessment,request);
//    }

}
