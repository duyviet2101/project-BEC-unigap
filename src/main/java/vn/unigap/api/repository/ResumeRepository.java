package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.unigap.api.entity.Resume;

import java.math.BigInteger;
import java.util.Date;

public interface ResumeRepository extends JpaRepository<Resume, BigInteger> {
    Integer countResumesByCreatedAtBetween(Date fromDate, Date toDate);

    @Query("SELECT count(A) from Resume A where DATE(A.createdAt) = DATE(?1) group by DATE(A.createdAt)")
    Integer countResumesByCreatedAt(Date createdAt);
}