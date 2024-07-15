package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.service.AnalyticsService;
import vn.unigap.common.controller.AbstractResponseController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsController extends AbstractResponseController {
    AnalyticsService analyticsService;

    @GetMapping(value = "news")
    public ResponseEntity<?> getNews(@Valid DateRangeDtoIn dateRangeDtoIn) {
        return responseEntity(() -> this.analyticsService.getNews(dateRangeDtoIn));
    }
}
