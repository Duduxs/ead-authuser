package com.ead.authuser.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final CachingConnectionFactory connectionFactory;

    @Autowired
    public RabbitMQConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final var template = new RabbitTemplate(connectionFactory);

        template.setMessageConverter(messageConverter());

        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        final var mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(mapper);
    }
}
