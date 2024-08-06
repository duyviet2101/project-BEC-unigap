package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDtoOut<T> {
    @Builder.Default
    Integer page = 1;
    @Builder.Default
    Integer pageSize = 10;
    @Builder.Default
    Long totalElements = 0L;
    @Builder.Default
    Long totalPages = 0L;
    @Builder.Default
    List<T> data = new ArrayList<>();

    public static <T> PageDtoOut<T> from(Integer page, Integer pageSize, Long totalElements, List<T> data) {
        Long totalPages = (long) Math.ceil((double) totalElements / pageSize);
        return PageDtoOut.<T>builder().page(page).pageSize(pageSize).totalElements(totalElements).totalPages(totalPages)
                .data(data).build();
    }
}
