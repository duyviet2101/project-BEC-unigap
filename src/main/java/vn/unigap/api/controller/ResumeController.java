package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;
import vn.unigap.api.service.ResumeService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;
import java.util.HashMap;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeController extends AbstractResponseController {
    ResumeService resumeService;

    @Operation(summary = "Lấy danh sách resumes", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        return responseEntity(() -> {
            this.resumeService.create(resumeDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Cập nhật resume", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id,
            @RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        return responseEntity(() -> {
            this.resumeService.update(id, resumeDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Lấy thông tin resume theo id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ResumeDtoOut.class))) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> this.resumeService.get(id));
    }

    @Operation(summary = "Lấy danh sách resumes", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ResponsePageResume.class))) })
    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn,
            @RequestParam(value = "seekerId", defaultValue = "-1") BigInteger seekerId) {
        return responseEntity(() -> this.resumeService.list(pageDtoIn, seekerId));
    }

    @Operation(summary = "Xóa resume", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.resumeService.delete(id);
            return new HashMap<>();
        });
    }

    static class ResponsePageResume extends vn.unigap.common.response.ApiResponse<PageDtoOut<ResumeDtoOut>> {
    }
}
