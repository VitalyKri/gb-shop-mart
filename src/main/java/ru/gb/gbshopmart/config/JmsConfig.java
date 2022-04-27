package ru.gb.gbshopmart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import ru.gb.gbapi.config.GbApiProperties;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    private final GbApiProperties gbApiProperties;

    public static final String ORDER_CHANGED_QUEUE ="OrderShop";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
