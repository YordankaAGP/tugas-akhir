package com.bcaf.tugasakhir.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LogRequest")
public class LogRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ClassName")
    private String className;

    @Column(name = "DataRequest",columnDefinition = "VARCHAR(8000) NOT NULL")
    private String dataRequest;

    @Column(name = "ErrorMessagez")
    private String errorMessagez;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 1")
    private Long createdBy;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date createdDate=new Date();

    public String getErrorMessagez() {
        return errorMessagez;
    }

    public void setErrorMessagez(String errorMessagez) {
        this.errorMessagez = errorMessagez;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDataRequest() {
        return dataRequest;
    }

    public void setDataRequest(String dataRequest) {
        this.dataRequest = dataRequest;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
