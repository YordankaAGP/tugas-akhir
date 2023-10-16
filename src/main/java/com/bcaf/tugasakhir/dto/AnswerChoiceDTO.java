package com.bcaf.tugasakhir.dto;

import javax.validation.constraints.NotNull;

public class AnswerChoiceDTO {
    @NotNull
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
