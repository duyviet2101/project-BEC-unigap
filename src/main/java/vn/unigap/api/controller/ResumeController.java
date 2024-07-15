package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.ResumeDtoIn;
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

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        return responseEntity(() -> {
            this.resumeService.create(resumeDtoIn);
            return new HashMap<>();
        });
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id,@RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        return responseEntity(() -> {
            this.resumeService.update(id, resumeDtoIn);
            return new HashMap<>();
        });
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> this.resumeService.get(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn, @RequestParam(value = "seekerId", defaultValue = "-1") BigInteger seekerId) {
        return responseEntity(() -> this.resumeService.list(pageDtoIn, seekerId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.resumeService.delete(id);
            return new HashMap<>();
        });
    }
}
