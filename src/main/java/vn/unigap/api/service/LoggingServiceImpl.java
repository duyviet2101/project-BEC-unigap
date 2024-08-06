package vn.unigap.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        log.info("REQUEST: " + httpServletRequest.getRequestURI() + " " + httpServletRequest.getMethod());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object body) {
        log.info("RESPONSE: " + httpServletRequest.getRequestURI() + " " + httpServletResponse.getStatus() + " "
                + httpServletResponse.getContentType());
    }
}
