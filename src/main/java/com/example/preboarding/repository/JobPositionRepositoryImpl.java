package com.example.preboarding.repository;

import com.example.preboarding.domain.JobPosition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JobPositionRepositoryImpl implements JobPositionRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public JobPosition findByIdDetail(Long jobPositionNum){
        return em.find(JobPosition.class,jobPositionNum);
    }

    @Override
    public List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum){
        return em.createQuery("select j from JobPosition as j inner join j.company as c where c.comNum = :comNum and j.num != :postNum",JobPosition.class)
                .setParameter("comNum",comNum)
                .setParameter("postNum",postNum)
                .getResultList();
    }


    public Page<JobPosition> searchPosition(String comName,String region,String nation, List<Long> roleNums, Pageable pageable) {
        String queryStr = "select j from JobPosition j inner join j.company c inner join j.companyRole cr where c.comName ilike :comName and c.region=:region and c.nation= :nation and cr.role.roleNum in :roleNums";
        TypedQuery<JobPosition> query = em.createQuery(queryStr, JobPosition.class)
                .setParameter("comName", "%" + comName + "%")
                .setParameter("roleNums",roleNums)
                .setParameter("region",region)
                .setParameter("nation",nation)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<JobPosition> jobPositions = query.getResultList();

        String countQueryStr = "select count(j) from JobPosition j inner join j.company c inner join j.companyRole cr where c.comName ilike :comName and cr.role.roleNum in :roleNums";
        TypedQuery<Long> countQuery = em.createQuery(countQueryStr, Long.class)
                .setParameter("comName", "%" + comName + "%")
                .setParameter("roleNums",roleNums)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        Long count = countQuery.getSingleResult();

        return new PageImpl<>(jobPositions, pageable, count);
    }
}
