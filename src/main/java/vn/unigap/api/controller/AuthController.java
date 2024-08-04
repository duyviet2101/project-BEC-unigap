package vn.unigap.api.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.unigap.api.dto.in.AuthLoginDtoIn;
import vn.unigap.api.dto.out.AuthLoginDtoOut;
import vn.unigap.api.service.AuthService;
import vn.unigap.common.controller.AbstractResponseController;
import vn.unigap.common.response.ApiResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController extends AbstractResponseController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDtoIn authLoginDtoIn) {
        return responseEntity(() -> authService.login(authLoginDtoIn));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthLoginDtoIn authLoginDtoIn) {
        return responseEntity(() -> authService.register(authLoginDtoIn));
    }
}
