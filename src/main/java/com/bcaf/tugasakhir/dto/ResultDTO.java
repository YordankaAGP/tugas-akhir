package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.UserAnswer;
import com.bcaf.tugasakhir.model.Usr;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class ResultDTO {
    private Long id;

    private double finalScore;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "AssessmentId")
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private Usr user;

    @OneToMany(mappedBy = "result")
    private List<UserAnswer> answers;
}
