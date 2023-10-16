package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.Choice;
import com.bcaf.tugasakhir.model.QuestionType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QuestionDTO {
    private Long id;

    @NotNull(message = "Text Question Tidak Boleh Null")
    @NotBlank(message = "Text Question Tidak Boleh Blank")
    @NotEmpty(message = "Text Question Tidak Boleh Kosong")
    @Length(min = 1,message = "Text Question Min 1")
    private String text;

    private String image;

    private QuestionType type = QuestionType.CHOICE;

    private List<QuestionChoiceDTO> choices;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<QuestionChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionChoiceDTO> choices) {
        this.choices = choices;
    }
}
