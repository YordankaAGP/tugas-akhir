package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepo extends JpaRepository<Result,Long> {
    public List<Result> findByAssessmentId(Long id);
    public List<Result> findByUserId(Long id);
}
