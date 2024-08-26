package vn.unigap.api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.unigap.api.entity.jpa.Resume;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, BigInteger> {
    Integer countResumesByCreatedAtBetween(Date fromDate, Date toDate);

    List<Resume> findBySeekerId(BigInteger id);
}