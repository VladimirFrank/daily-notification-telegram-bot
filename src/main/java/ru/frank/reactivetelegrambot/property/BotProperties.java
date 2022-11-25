package ru.frank.reactivetelegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "bot")
@ConstructorBinding
@Data
public class BotProperties {
    private final String username;
    private final String token;
}
