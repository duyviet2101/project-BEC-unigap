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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;
import java.util.HashMap;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Employer", description = "Quản lý nhà tuyển dụng")
public class EmployerController extends AbstractResponseController {
    EmployerService employerService;

    @Operation(summary = "Lấy danh sách nhà tuyển dụng", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ResponsePageEmployer.class)))
    })
    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn) {
        return responseEntity(() -> {
            return this.employerService.list(pageDtoIn);
        });
    }

    @Operation(summary = "Lấy thông tin employee theo id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EmployerDtoOut.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            return this.employerService.get(id);
        });
    }

    @Operation(summary = "Thêm mới nhà tuyển dụng", responses = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HashMap.class)))
    })
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid EmployerDtoIn employerDtoIn) {
        return responseEntity(() -> {
           this.employerService.create(employerDtoIn);
           return new HashMap<>();
        }, HttpStatus.CREATED);
    }

    @Operation(summary = "Cập nhật nhà tuyển dụng", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id,
                                    @RequestBody @Valid UpdateEmployerDtoIn updateEmployerDtoIn) {
        return responseEntity(() -> {
            this.employerService.update(id, updateEmployerDtoIn);
            return new HashMap<>();
        });
    }

    @Operation(summary = "Xóa nhà tuyển dụng", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HashMap.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.employerService.delete(id);
            return new HashMap<>();
        });
    }

    static class ResponsePageEmployer extends PageDtoOut<EmployerDtoOut> {
    }
}
