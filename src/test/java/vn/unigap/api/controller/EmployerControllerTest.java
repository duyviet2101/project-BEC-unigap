package vn.unigap.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.response.ApiResponse;

import java.math.BigInteger;
import java.util.Date;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployerService employerService;

    EmployerDtoIn employerDtoIn;
    EmployerDtoOut employerDtoOut;

    @BeforeEach
    void initData() {

        employerDtoIn = EmployerDtoIn.builder()
                .email("aaa@a.a")
                .name("Viettt")
                .province(1111)
                .description("AAAA")
                .build();

        employerDtoOut = EmployerDtoOut.builder()
                .id(BigInteger.valueOf(11111))
                .email("aaa@a.a")
                .name("Viettt")
                .province(1111)
                .description("AAAA")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

    }

    @Test
    void create_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(employerDtoIn);
        Mockito.when(employerService.create(ArgumentMatchers.any()))
                .thenReturn(employerDtoOut);


        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/employers").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(result -> {
                    var response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<ApiResponse<EmployerDtoOut>>() {
                            });
                    Assertions.assertEquals(employerDtoOut, response.getObject());
                    Assertions.assertEquals(201, response.getStatusCode());
                });
    }

}