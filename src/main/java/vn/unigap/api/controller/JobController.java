package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.JobService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;
import java.util.HashMap;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobController extends AbstractResponseController {
    JobService jobService;

    @Operation(summary = "Lấy danh sách jobs", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ResponsePageJob.class))) })
    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn, BigInteger employerId) {
        return responseEntity(() -> this.jobService.list(pageDtoIn, employerId));
    }

    @Operation(summary = "Lấy thông tin job theo id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JobDtoOut.class))) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger employerId) {
        return responseEntity(() -> this.jobService.get(employerId));
    }

    @Operation(summary = "Thêm mới job", responses = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid JobDtoIn jobDtoIn) {
        return responseEntity(() -> {
            this.jobService.create(jobDtoIn);
            return new HashMap<>();
        }, HttpStatus.CREATED);
    }

    @Operation(summary = "Cập nhật job", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id, @RequestBody @Valid JobDtoIn jobDtoIn) {
        return responseEntity(() -> {
            this.jobService.update(id, jobDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Xóa job", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable BigInteger id) {
        return responseEntity(() -> {
            this.jobService.delete(id);
            return new HashMap<>();
        });
    }

    static class ResponsePageJob extends vn.unigap.common.response.ApiResponse<PageDtoOut<JobDtoOut>> {
    }
}
