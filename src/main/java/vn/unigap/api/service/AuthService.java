package vn.unigap.api.service;

import vn.unigap.api.dto.in.AuthLoginDtoIn;
import vn.unigap.api.dto.out.AuthLoginDtoOut;
import vn.unigap.api.dto.out.AuthRegisterDtoOut;

public interface AuthService {
    AuthLoginDtoOut login(AuthLoginDtoIn authLoginDtoIn);

    AuthRegisterDtoOut register(AuthLoginDtoIn authLoginDtoIn);
}
