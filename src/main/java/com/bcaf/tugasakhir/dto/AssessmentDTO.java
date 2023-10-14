package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Question;
import com.bcaf.tugasakhir.model.Result;
import com.bcaf.tugasakhir.model.Usr;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class AssessmentDTO {
    private Long id;

    @NotNull(message = "Title Assessment Tidak Boleh Null")
    @NotBlank(message = "Title Assessment Tidak Boleh Blank")
    @NotEmpty(message = "Title Assessment Tidak Boleh Kosong")
    @Length(min = 5,max = 25,message = "Title Assessment Min 5 Maks 25 Karakter")
    private String title;

    private String password;

    @NotNull(message = "End Date harus diisi")
    @Future(message = "End Date harus di masa depan")
    private Date endDate;

    @JsonBackReference
    private List<Usr> participants;

    private List<Result> results;

    private List<Question> questions;

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

    public List<Usr> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Usr> participants) {
        this.participants = participants;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
