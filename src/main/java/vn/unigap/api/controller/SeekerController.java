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
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;
import vn.unigap.api.service.SeekerService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;
import java.util.HashMap;

@RestController
@RequestMapping("/seekers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeekerController extends AbstractResponseController {
    SeekerService seekerService;

    @Operation(summary = "Thêm mới người tìm việc", responses = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid SeekerDtoIn seekerDtoIn) {
        return responseEntity(() -> {
            this.seekerService.create(seekerDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Cập nhật người tìm việc", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id,
            @RequestBody @Valid SeekerDtoIn seekerDtoIn) {
        return responseEntity(() -> {
            this.seekerService.update(id, seekerDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Lấy danh sách người tìm việc", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ResponsePageSeeker.class))) })
    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn,
            @RequestParam(value = "provinceId", defaultValue = "-1") Integer provinceId) {
        return responseEntity(() -> this.seekerService.list(pageDtoIn, provinceId));
    }

    @Operation(summary = "Lấy thông tin người tìm việc theo id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SeekerDtoOut.class))) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> this.seekerService.get(id));
    }

    @Operation(summary = "Xóa người tìm việc", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class))) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.seekerService.delete(id);
            return new HashMap<>();
        });
    }

    static class ResponsePageSeeker extends vn.unigap.common.response.ApiResponse<PageDtoOut<SeekerDtoOut>> {
    }
}
