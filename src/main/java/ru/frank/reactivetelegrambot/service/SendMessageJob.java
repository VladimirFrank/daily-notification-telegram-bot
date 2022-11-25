package ru.frank.reactivetelegrambot.service;

import lombok.RequiredArgsConstructor;
import ru.frank.reactivetelegrambot.bot.ScrumBot;

@RequiredArgsConstructor
public class SendMessageJob implements Runnable {
    private final ScrumBot sender;
    private final String groupId;
    private final String messageToSend;

    @Override
    public void run() {
        System.out.println("Time to send message: " + messageToSend);
        sender.sendMessage(
                groupId,
                messageToSend
        );
    }
}
