package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepo extends JpaRepository<Assessment,Long> {
    public List<Assessment> findByTitleContains(String n);

    public List<Assessment> findByParticipantsId(Long id);
}
