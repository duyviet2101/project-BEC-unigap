package vn.unigap.api.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.unigap.api.entity.jpa.Employer;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployerDtoOut {
    BigInteger id;
    String email;
    String name;
    Integer province;
    String description;
    Date createdAt;
    Date updatedAt;

    public static EmployerDtoOut from(Employer employer) {
        return EmployerDtoOut.builder().id(employer.getId()).email(employer.getEmail()).name(employer.getName())
                .province(employer.getProvince()).description(employer.getDescription())
                .createdAt(employer.getCreatedAt()).updatedAt(employer.getUpdatedAt()).build();
    }
}
