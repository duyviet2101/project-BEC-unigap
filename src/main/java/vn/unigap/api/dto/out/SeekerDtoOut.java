package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerDtoOut {
    Integer id;
    String name;
    String birthday;
    String address;
    Integer provinceId;
    String provinceName;
}
