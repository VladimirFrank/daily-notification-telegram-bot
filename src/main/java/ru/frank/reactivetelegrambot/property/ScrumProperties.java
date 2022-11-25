package ru.frank.reactivetelegrambot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "scrum")
@ConstructorBinding
@Data
public class ScrumProperties {
    private final List<String> groupIds;
    private final Daily daily;
    private final Wednesday wednesday;

    @Data
    public static class Daily {
        private final String cronExpression;
        private final String message;
        private final String link;
    }

    @Data
    public static class Wednesday {
        private final String cronExpression;
        private final String message;
    }
}
