package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.Choice;

import javax.persistence.*;
import java.util.List;

public class QuestionChoiceDTO {
    private Long id;

    private String text;

    private String image;

    private String type;

    private List<Choice> choices;

    private Assessment assessment;
}
