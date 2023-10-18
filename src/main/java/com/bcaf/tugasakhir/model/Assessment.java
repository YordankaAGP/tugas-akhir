package com.bcaf.tugasakhir.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Assessment")
public class Assessment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Password")
    private String password;

    @Column(name = "EndDate")
    private Date endDate;

    @JsonIgnoreProperties("assessments")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MapUserAssessment", joinColumns = @JoinColumn(name = "AssessmentId"), inverseJoinColumns = @JoinColumn(name = "UserId"))
    private List<Usr> participants;

    @JsonManagedReference(value = "assessment-result")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;

    @JsonManagedReference(value = "assessment-question")
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
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
