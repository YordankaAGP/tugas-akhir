package com.bcaf.tugasakhir.dto;

import javax.validation.constraints.NotNull;

public class AnswerChoiceDTO {
    @NotNull
    private Long id;

    private String value;

    private boolean isTrue;

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

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
