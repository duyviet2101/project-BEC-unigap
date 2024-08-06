package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.dto.out.AnalyticsJobRecommendationDtoOut;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;
import vn.unigap.api.service.AnalyticsService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Analytics", description = "Analytics")
public class AnalyticsController extends AbstractResponseController {
    AnalyticsService analyticsService;

    @Operation(summary = "Lấy thông tin analytics", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsNewsDtoOut.class))) })
    @GetMapping(value = "news")
    public ResponseEntity<?> getNews(@Valid DateRangeDtoIn dateRangeDtoIn) {
        return responseEntity(() -> this.analyticsService.getNews(dateRangeDtoIn));
    }

    @Operation(summary = "Lấy thông tin recommendations cho job", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsJobRecommendationDtoOut.class))) })
    @GetMapping(value = "/jobs/{id}/recommendations")
    public ResponseEntity<?> getRecommendationsForJob(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> this.analyticsService.getRecommendationsForJob(id));
    }
}
