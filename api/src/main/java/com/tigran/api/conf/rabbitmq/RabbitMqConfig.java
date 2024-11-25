package com.tigran.api.conf.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:40 PM
 */
@Configuration
public class RabbitMqConfig {

    private final String thingsExchange;
    private final String thingsDataQueue;

    RabbitMqConfig(
            @Value("${dispatch.rabbitmq.things.exchange}") final String thingsExchange,
            @Value("${dispatch.rabbitmq.things.data.queue}") final String thingsDataQueue) {
        this.thingsExchange = thingsExchange;
        this.thingsDataQueue = thingsDataQueue;
    }

    @Bean(name = "thingDataExchange")
    public FanoutExchange thingsExchange() {
        return new FanoutExchange(thingsExchange);
    }

    @Bean(name = "thingDataQueue")
    public Queue thingDataQueue() {
        return new Queue(thingsDataQueue, true);
    }

    @Bean(name = "thingDataBinding")
    public Binding thingDataBinding() {
        return BindingBuilder
                .bind(thingDataQueue())
                .to(thingsExchange());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
