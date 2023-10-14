package com.bcaf.tugasakhir.model;

import javax.persistence.*;

@Entity
@Table(name = "UserAnswer")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Value")
    private String value;

    @Column(name = "Score")
    private double score;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private Usr user;

    @ManyToOne
    @JoinColumn(name = "ResultId")
    private Result result;
    // getters and setters

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Usr getUser() {
        return user;
    }

    public void setUser(Usr user) {
        this.user = user;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
