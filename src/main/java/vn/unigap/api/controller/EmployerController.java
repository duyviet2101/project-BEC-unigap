package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.controller.AbstractResponseController;

import java.math.BigInteger;
import java.util.HashMap;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerController extends AbstractResponseController {
    EmployerService employerService;

    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn) {
        return responseEntity(() -> {
            return this.employerService.list(pageDtoIn);
        });
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            return this.employerService.get(id);
        });
    }

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid EmployerDtoIn employerDtoIn) {
        return responseEntity(() -> {
           return this.employerService.create(employerDtoIn);
        }, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id,
                                    @RequestBody @Valid UpdateEmployerDtoIn updateEmployerDtoIn) {
        return responseEntity(() -> {
            return this.employerService.update(id, updateEmployerDtoIn);
        });
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.employerService.delete(id);
            return new HashMap<>();
        });
    }
}
