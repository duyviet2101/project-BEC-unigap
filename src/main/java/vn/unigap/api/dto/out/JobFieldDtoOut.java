package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobFieldDtoOut {
    Integer id;
    String name;
}
