package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.JobField;

import java.util.List;

@Repository
public interface JobFieldRepository extends JpaRepository<JobField, Integer> {
    Integer countByIdIn(List<Integer> ids);
    boolean existsById(Integer id);
}
