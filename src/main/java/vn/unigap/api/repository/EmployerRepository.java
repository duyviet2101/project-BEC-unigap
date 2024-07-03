package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, BigInteger> {
    Optional<Employer> findByEmail(String email);

    Page<Employer> findAll(Pageable page);
}