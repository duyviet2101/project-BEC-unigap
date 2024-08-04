package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.AuthLoginDtoIn;
import vn.unigap.api.dto.out.AuthLoginDtoOut;
import vn.unigap.api.dto.out.AuthRegisterDtoOut;
import vn.unigap.api.entity.User;
import vn.unigap.api.repository.UserRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtEncoder jwtEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtEncoder jwtEncoder, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthLoginDtoOut login(AuthLoginDtoIn authLoginDtoIn) {
        User user = userRepository.findUserByUsername(authLoginDtoIn.getUsername())
            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "User not found!"));

        boolean isAuthenticated = passwordEncoder.matches(authLoginDtoIn.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Password or username is invalid!");

        return AuthLoginDtoOut.builder()
                .accessToken(grantAccessToken(grantAccessToken(user.getUsername())))
                .build();
    }

    @Override
    public AuthRegisterDtoOut register(AuthLoginDtoIn authLoginDtoIn) {
        if (userRepository.existsUserByUsername(authLoginDtoIn.getUsername()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "User existed!");

        User user = User.builder()
                .username(authLoginDtoIn.getUsername())
                .password(authLoginDtoIn.getPassword())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return AuthRegisterDtoOut.builder()
                .username(user.getUsername())
                .build();
    }

    private String grantAccessToken(String username) {
        long iat = System.currentTimeMillis() / 1000;
        long exp = iat + Duration.ofHours(8).toSeconds();

        JwtEncoderParameters parameters = JwtEncoderParameters
                //header
                .from(JwsHeader.with(SignatureAlgorithm.RS256).build(),
                //payload
                JwtClaimsSet.builder().subject(username).issuedAt(Instant.ofEpochSecond(iat))
                        .expiresAt(Instant.ofEpochSecond(exp)).claim("user_name", username)
                        .claim("scope", List.of("ADMIN")).build());
        try {
            return jwtEncoder.encode(parameters).getTokenValue();
        } catch (JwtEncodingException e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
