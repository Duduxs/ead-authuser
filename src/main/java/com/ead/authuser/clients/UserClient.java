package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;

@Log4j2
@Component
public class UserClient {

    private final String REQUEST_URI = "http://localhost:8082";

    private final RestTemplate template;

    public UserClient(final RestTemplate template) {
        this.template = template;
    }

    public Page<CourseDTO> findAllBy(final UUID userId, final Pageable pageable) {

        List<CourseDTO> courseDTO = Collections.emptyList();

        final String url = REQUEST_URI
                + "/courses?userId=" + userId
                + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

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

        return new PageImpl<CourseDTO>(courseDTO);

    }
}
