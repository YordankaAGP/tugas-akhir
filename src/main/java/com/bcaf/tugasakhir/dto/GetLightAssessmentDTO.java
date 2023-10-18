package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Result;

import java.util.Date;
import java.util.List;

public class GetLightAssessmentDTO {
    private Long id;

    private String title;

    private String password;

    private Date endDate;

    private List<ResultByUserDTO> results;

    private List<QuestionDTO> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public List<ResultByUserDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultByUserDTO> results) {
        this.results = results;
    }
}
