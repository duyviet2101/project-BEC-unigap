package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.unigap.api.entity.Resume;

import java.math.BigInteger;

public interface ResumeRepository extends JpaRepository<Resume, BigInteger> {
}
