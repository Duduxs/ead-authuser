package com.ead.authuser.consumers;

import com.ead.authuser.dtos.PaymentEventDTO;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.PaymentControl;
import com.ead.authuser.mappers.UserMapper;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.RoleService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.ead.authuser.enums.RoleType.ROLE_STUDENT;
import static com.ead.authuser.enums.UserType.STUDENT;
import static com.ead.authuser.enums.UserType.USER;

@Component
public class PaymentConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserEventPublisher publisher;

    @Autowired
    UserMapper mapper;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.payment.name}",
                            durable = "true"),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.paymentEvent.name}",
                            type = ExchangeTypes.FANOUT,
                            ignoreDeclarationExceptions = "true"
                    )
            )
    )
    public void listenPaymentEvent(@Payload PaymentEventDTO dto) {
        UserModel model = userRepository.findById(dto.getUserId()).get();
        var roleModel = roleService.findByName(ROLE_STUDENT);

        switch (PaymentControl.valueOf(dto.getPaymentControl())) {
            case EFFECTED -> {
                if (model.getType().equals(USER)) {
                    model.setType(STUDENT);
                    model.getRoles().add(roleModel);
                    userRepository.save(model);
                    publisher.publishEvent(mapper.toUserEventDTO(model, ActionType.UPDATE));
                }
            }
            case REFUSED -> {
                if (model.getType().equals(STUDENT)) {
                    model.setType(USER);
                    model.getRoles().remove(roleModel);
                    userRepository.save(model);
                    publisher.publishEvent(mapper.toUserEventDTO(model, ActionType.UPDATE));
                }
            }
            case ERROR -> System.out.println("Payment with error!");
        }
    }
}
