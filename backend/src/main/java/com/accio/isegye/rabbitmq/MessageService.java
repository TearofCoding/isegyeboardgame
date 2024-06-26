package com.accio.isegye.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

//    /**
//     * Queue로 메시지를 발행
//     *
//     * @param messageDto 발행할 메시지의 DTO 객체
//     */
//    public void sendMessage(MessageDto messageDto) {
//        log.info("message sent: {}", messageDto.toString());
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
//    }

    /**
     * Queue에서 메시지를 구독
     *
     * @param message 구독한 메시지를 담고 있는 MessageDto 객체
     */
    @RabbitListener(queues = "isegye.queue")
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
    }
}
