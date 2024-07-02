package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagingResponse<T> {
    Integer page;
    Integer pageSize;
    Long totalElements;
    Long totalPages;
    ArrayList<T> data;
}
