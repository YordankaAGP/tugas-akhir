package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.Choice;
import com.bcaf.tugasakhir.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

public class QuestionChoiceDTO {
    private Long id;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
