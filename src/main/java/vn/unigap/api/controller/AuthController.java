package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Auth", description = "Auth")
public class AuthController extends AbstractResponseController {

    AuthService authService;

    @Operation(summary = "Đăng nhập", responses = { @ApiResponse(responseCode = "200", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthLoginDtoOut.class)) }) })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDtoIn authLoginDtoIn) {
        return responseEntity(() -> authService.login(authLoginDtoIn));
    }

    @Operation(summary = "Đăng ký", responses = { @ApiResponse(responseCode = "200", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthLoginDtoOut.class)) }) })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthLoginDtoIn authLoginDtoIn) {
        return responseEntity(() -> authService.register(authLoginDtoIn));
    }
}
