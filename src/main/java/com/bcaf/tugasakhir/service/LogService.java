package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.model.LogRequest;
import com.bcaf.tugasakhir.repo.LogRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogService {

    private LogRequestRepo logRequestRepo;

    @Autowired
    public LogService(LogRequestRepo logRequestRepo) {
        this.logRequestRepo = logRequestRepo;
    }

    public void saveLog(LogRequest logRequest)
    {
        logRequestRepo.save(logRequest);
    }
}