package com.example.preboarding.repository.apply;

import com.example.preboarding.domain.Apply;
import com.example.preboarding.dto.ApplyUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply,Long>, ApplyRepositoryCustom {
    List<ApplyUserDTO> findByJobPosition(Long applyPostNum);

}
