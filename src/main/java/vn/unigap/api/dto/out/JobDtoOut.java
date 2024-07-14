package vn.unigap.api.dto.out;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.unigap.api.entity.Job;
import vn.unigap.api.service.EmployerService;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobDtoOut {

    BigInteger id;
    String title;
    Integer quantity;
    String description;
    List<JobFieldDtoOut> fields;
    List<JobProvinceDtoOut> provinces;
    Integer salary;
    Date expiredAt;
    BigInteger employerId;
    String employerName;

    public static JobDtoOut from(Job job, String employerName) {
        return JobDtoOut.builder()
                .id(job.getId())
                .title(job.getTitle())
                .employerId(job.getEmployerId())
                .quantity(job.getQuantity())
                .description(job.getDescription())
                .salary(job.getSalary())
//                .fields(job.getFields())
//                .provinces(job.getProvinces())
                .employerName(employerName)
                .expiredAt(job.getExpiredAt())
                .build();
    }
}