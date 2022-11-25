package ru.frank.reactivetelegrambot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.frank.reactivetelegrambot.property.BotProperties;
import ru.frank.reactivetelegrambot.service.MemeService;
import ru.frank.reactivetelegrambot.service.SchedulerService;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ScrumBot extends TelegramLongPollingBot {
    private final BotProperties properties;
    private final SchedulerService schedulerService;
    private final MemeService memeService;

    @PostConstruct
    public void init() {
        schedulerService.scheduleScrumForConfiguredGroups(this);
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        String text = message.getText();
        var command = BotCommand.findCommand(text);
        switch (command) {
            case START_SCRUM -> {
                scheduleDaily(message);
                scheduleWednesday(message);
            }
            case STOP_SCRUM -> {
                stopDaily(message);
                stopWednesday(message);
            }
            case GET_MEME -> {
                sendMeme(message);
            }
            default -> {
                throw new IllegalArgumentException("Unsupported command");
            }
        }
    }

    @SneakyThrows
    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage =
                SendMessage.builder()
                        .chatId(chatId)
                        .text(text)
                        .build();

        execute(sendMessage);
    }

    @SneakyThrows
    private void sendMeme(Message message) {
        var file = new InputFile();
        file.setMedia(memeService.loadMeme());

        SendPhoto sendPhoto =
                SendPhoto.builder()
                        .chatId(message.getChat().getId().toString())
                        .photo(file)
                        .build();

        execute(sendPhoto);
    }

    private void scheduleDaily(Message message) {
        String groupId = message.getChat().getId().toString();

        schedulerService.scheduleDaily(this, groupId);
    }

    private void stopDaily(Message message) {
        String groupId = message.getChat().getId().toString();

        schedulerService.stopDaily(groupId);
    }

    private void scheduleWednesday(Message message) {
        String groupId = message.getChat().getId().toString();

        schedulerService.scheduleWednesday(this, groupId);
    }

    private void stopWednesday(Message message) {
        String groupId = message.getChat().getId().toString();

        schedulerService.stopWednesday(groupId);
    }
}
