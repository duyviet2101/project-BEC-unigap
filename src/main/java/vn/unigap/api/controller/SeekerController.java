package vn.unigap.api.controller;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.SeekerDtoIn;
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

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid SeekerDtoIn seekerDtoIn) {
        return responseEntity(() -> {
            this.seekerService.create(seekerDtoIn);
            return new HashMap<>();
        });
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") BigInteger id, @RequestBody @Valid SeekerDtoIn seekerDtoIn) {
        return responseEntity(() -> {
           this.seekerService.update(id, seekerDtoIn);
           return new HashMap<>();
        });
    }

    @GetMapping(value = "")
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn, @RequestParam(value = "provinceId", defaultValue = "-1") Integer provinceId) {
        return responseEntity(() -> this.seekerService.list(pageDtoIn, provinceId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> this.seekerService.get(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") BigInteger id) {
        return responseEntity(() -> {
            this.seekerService.delete(id);
            return new HashMap<>();
        });
    }
}
