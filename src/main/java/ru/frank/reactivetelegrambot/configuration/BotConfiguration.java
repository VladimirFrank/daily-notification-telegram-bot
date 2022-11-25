package ru.frank.reactivetelegrambot.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.frank.reactivetelegrambot.bot.ScrumBot;

@Configuration
public class BotConfiguration {
    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi(ScrumBot scrumBot) {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(scrumBot);

        return botsApi;
    }
}
