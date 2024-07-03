package vn.unigap.api.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployerDtoIn {
    @Email
    @NotEmpty
    @Size(max = 255)
    String email;

    @NotEmpty
    @Size(max = 255)
    String name;

    @NotNull
    Integer provinceId;

    String description;
}
