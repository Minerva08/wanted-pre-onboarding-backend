package com.example.preboarding.repository;

import com.example.preboarding.domain.JobPosition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition,Long>, JobPositionRepositoryCustom{
    JobPosition findByIdDetail(Long jobPositionNum);
    List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum);


}
