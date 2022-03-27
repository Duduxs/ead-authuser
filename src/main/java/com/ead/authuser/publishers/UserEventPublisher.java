package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Value(value = "${ead.broker.exchange.userEvent}")
    private final String exchangeName = "";

    private final RabbitTemplate template;

    private final Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    public UserEventPublisher(final RabbitTemplate template) {
        this.template = template;
    }

    public void publishEvent(final UserEventDTO dto) {

        try {
            log.info("[publishEvent] INIT -> eventType {} | dto {} ", dto.actionType(), dto);
            template.convertAndSend(exchangeName, "", dto);
        } catch (final AmqpException e) {
            log.error("[publishEvent] ERROR -> Something went wrong {}", e.getMessage());
            throw e;
        } finally {
            log.info("[publishEvent] FINISH");
        }
    }
}
