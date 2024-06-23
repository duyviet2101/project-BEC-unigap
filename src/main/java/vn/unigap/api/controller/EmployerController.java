package vn.unigap.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.dto.out.ApiResponse;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerController {
    EmployerService employerService;

    @PostMapping
    ApiResponse<Employer> createEmployer(@RequestBody EmployerCreationRequest request) {
        ApiResponse<Employer> apiResponse = new ApiResponse<>();

        apiResponse.setObject(employerService.createEmployer(request));
        apiResponse.setMessage("Create Employer Success!");

        return apiResponse;
    }
}
