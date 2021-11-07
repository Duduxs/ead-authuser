package com.ead.authuser.configurations;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class ResolverConfig extends WebMvcConfigurationSupport {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        final var pageableResolver = new PageableHandlerMethodArgumentResolver();
        final var filterResolver = new SpecificationArgumentResolver();

        argumentResolvers.addAll(List.of(pageableResolver, filterResolver));

        super.addArgumentResolvers(argumentResolvers);
    }

}
