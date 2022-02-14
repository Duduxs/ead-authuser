package com.ead.authuser.services.impl;

import com.ead.authuser.services.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Value("${ead.api.url.course}")
    private String courseUri = "";

    public String createUrlGetAllCoursesByUser(final UUID userId, final Pageable pageable) {
        return courseUri
                + "/courses?userId=" + userId
                + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }

}
