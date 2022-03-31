package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;

@Log4j2
@Component
public class CourseClient {

    private final RestTemplate template;

    private final UtilsService utilsService;

    public CourseClient(final RestTemplate template, UtilsService utilsService) {
        this.template = template;
        this.utilsService = utilsService;
    }

    //@Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    //@CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "circuitBreakerFallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDTO> findAllBy(final UUID userId, final Pageable pageable) {

        List<CourseDTO> courseDTO = Collections.emptyList();

        final String url = utilsService.createUrlGetAllCoursesByUser(userId, pageable);

        log.debug("[GET] INFO - URL: {}", url);
        log.info("[GET] INFO - URL: {}", url);

        try {

            final var responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};

            final ResponseEntity<ResponsePageDTO<CourseDTO>> result = template.exchange(
                    url,
                    GET,
                    null,
                    responseType
            );

            courseDTO = result.getBody().getContent();

             log.debug("[GET] INFO - Number of elements {}", courseDTO.size());

        } catch(final HttpStatusCodeException e) {

            log.error("[GET] ERROR - Something went wrong: {}", e);

        }

        log.info("[GET] FINISH - findAllBy() ending request for userId {}", userId);

        return new PageImpl<>(courseDTO);

    }

    public Page<CourseDTO> circuitBreakerFallback(final UUID userId, final Pageable pageable, final Throwable t) {
        log.error("circuitBreakerFallback() cause - {}", t.toString());
        List<CourseDTO> search = new ArrayList<>();
        return new PageImpl<CourseDTO>(search);
    }

    public Page<CourseDTO> retryFallback(final UUID userId, final Pageable pageable, final Throwable t) {
        log.error("retryFallback() cause - {}", t.toString());
        List<CourseDTO> search = new ArrayList<>();
        return new PageImpl<CourseDTO>(search);
    }
}
