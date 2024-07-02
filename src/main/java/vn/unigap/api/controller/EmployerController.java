package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.dto.in.EmployerUpdateRequest;
import vn.unigap.api.dto.in.PageRequest;
import vn.unigap.api.dto.out.ApiResponse;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.dto.out.PagingResponse;
import vn.unigap.api.service.EmployerService;

import java.math.BigInteger;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerController {
    EmployerService employerService;

    @PostMapping
    ApiResponse<EmployerResponse> createEmployer(@Valid @RequestBody EmployerCreationRequest request) {
        ApiResponse<EmployerResponse> apiResponse = new ApiResponse<>();

        apiResponse.setObject(employerService.createEmployer(request));
        apiResponse.setMessage("Create Employer Success!");

        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<EmployerResponse> updateEmployer(@PathVariable(value = "id") BigInteger employerId, @Valid @RequestBody EmployerUpdateRequest request) {
        ApiResponse<EmployerResponse> apiResponse = new ApiResponse<>();

        apiResponse.setObject(employerService.updateEmployer(employerId, request));
        apiResponse.setMessage("Update Employer Success!");

        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<EmployerResponse> getEmployer(@PathVariable(value = "id") BigInteger employerId) {
        return ApiResponse.<EmployerResponse>builder()
                .object(employerService.getEmployer(employerId))
                .message("Get employer success!")
                .build();
    }

    @GetMapping("")
    ApiResponse<PagingResponse<EmployerResponse>> getEmployers(PageRequest pageRequest) {
        return ApiResponse.<PagingResponse<EmployerResponse>>builder()
                .object(employerService.getEmployers(pageRequest.getPage(), pageRequest.getPageSize()))
                .message("Get list success!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse deleteEmployer(@PathVariable(value = "id") BigInteger employerId) {
        employerService.deleteEmployer(employerId);
        return ApiResponse.builder()
                .message("Deleted!")
                .build();
    }
}
