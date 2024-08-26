package vn.unigap.api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.jpa.JobProvince;

import java.util.List;

@Repository
public interface JobProvinceRepository extends JpaRepository<JobProvince, Integer> {
    Integer countByIdIn(List<Integer> ids);

    boolean existsJobProvinceById(Integer id);

    JobProvince getJobProvinceById(Integer id);
}