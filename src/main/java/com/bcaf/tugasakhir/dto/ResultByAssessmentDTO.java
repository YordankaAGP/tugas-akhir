package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Answer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ResultByAssessmentDTO {
    private Long id;

    private double finalScore;

    private Date createdAt = new Date();

    private ResultUserDTO user;

    @NotNull(message = "Answers Tidak Boleh Null!")
    @NotEmpty(message = "Answers Tidak Boleh Kosong!")
    @Valid
    private List<Answer> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ResultUserDTO getUser() {
        return user;
    }

    public void setUser(ResultUserDTO user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
