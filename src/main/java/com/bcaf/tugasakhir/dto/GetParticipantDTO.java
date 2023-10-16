package com.bcaf.tugasakhir.dto;

import com.bcaf.tugasakhir.model.Assessment;
import com.bcaf.tugasakhir.model.Result;

import java.util.List;

public class GetParticipantDTO {
    private Long id;

    private String username;

    private String email;

    private String name;

    private List<Result> results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
