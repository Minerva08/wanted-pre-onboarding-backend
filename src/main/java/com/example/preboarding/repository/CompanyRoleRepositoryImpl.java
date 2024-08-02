package com.example.preboarding.repository;

import com.example.preboarding.domain.CompanyRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class CompanyRoleRepositoryImpl implements CompanyRoleRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public CompanyRole findCompanyRoleById(Long id) {
        return em.find(CompanyRole.class, id);
    }


    @Override
    public Long findByCompanyNumAndRoleNum(Long comNum, Long roleNum) {
        System.out.println("comNum"+comNum);
        System.out.println("roleNum"+roleNum);
        String jpql = "SELECT cr.companyRoleNum FROM CompanyRole cr WHERE cr.company.comNum = :comNum AND cr.role.roleNum = :roleNum";
        TypedQuery<Long> query = em.createQuery(jpql,Long.class);
        query.setParameter("comNum", comNum);
        query.setParameter("roleNum", roleNum);
        return query.getSingleResult();
    }


}
