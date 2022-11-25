package ru.frank.reactivetelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.frank.reactivetelegrambot.property.BotProperties;
import ru.frank.reactivetelegrambot.property.ScrumProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		BotProperties.class,
		ScrumProperties.class
})
@EnableScheduling
@EnableFeignClients
public class ReactiveTelegramBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReactiveTelegramBotApplication.class, args);
	}
}
