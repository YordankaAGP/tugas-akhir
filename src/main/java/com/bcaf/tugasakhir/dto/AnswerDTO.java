package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Choice;
import com.bcaf.tugasakhir.model.Question;
import com.bcaf.tugasakhir.model.Result;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

public class AnswerDTO {

    private Long id;

    private String text;

    @NotNull(message = "Choice Tidak Boleh Null")
    private Choice choice;

    @NotNull(message = "Question Tidak Boleh Null")
    private Question question;

    @Min(0)
    @Max(1)
    private Float score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
