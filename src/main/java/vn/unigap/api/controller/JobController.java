package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
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

    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn, BigInteger employerId) {
        return responseEntity(() -> this.jobService.list(pageDtoIn, employerId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger employerId) {
        return responseEntity(() -> this.jobService.get(employerId));
    }

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid JobDtoIn jobDtoIn) {
        return responseEntity(() -> {
            this.jobService.create(jobDtoIn);
            return new HashMap<>();
        }, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id, @RequestBody @Valid JobDtoIn jobDtoIn) {
        return responseEntity(() -> {
            this.jobService.update(id, jobDtoIn);
            return new HashMap<>();
        });
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable BigInteger id) {
        return responseEntity(() -> {
            this.jobService.delete(id);
            return new HashMap<>();
        });
    }
}
