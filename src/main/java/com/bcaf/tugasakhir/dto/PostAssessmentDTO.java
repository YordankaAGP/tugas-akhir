package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Question;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class PostAssessmentDTO {
    private Long id;

    @NotNull(message = "Title Assessment Tidak Boleh Null")
    @NotBlank(message = "Title Assessment Tidak Boleh Blank")
    @NotEmpty(message = "Title Assessment Tidak Boleh Kosong")
    @Length(min = 1, message = "Title Assessment Min 5")
    private String title;

    private String password;

    @NotNull(message = "End Date harus diisi")
    @Future(message = "End Date harus di masa depan")
    private Date endDate;

    @Valid
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
}
