package com.bcaf.tugasakhir.model;

import javax.persistence.*;

@Entity
@Table(name = "Choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Value")
    private String value;
    
    @Column(name = "IsTrue")
    private boolean isTrue;
    
    
    // getters and setters

    @ManyToOne
    @JoinColumn(name = "QuestionId")
    private Question question;

    
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
    public boolean isTrue() {
        return isTrue;
    }
    public void setTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    

    
}

