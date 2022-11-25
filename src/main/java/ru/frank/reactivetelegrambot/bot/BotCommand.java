package ru.frank.reactivetelegrambot.bot;

import java.util.Arrays;

public enum BotCommand {
    START_SCRUM("/start_scrum"),
    STOP_SCRUM("/stop_scrum"),
    GET_MEME("/meme");

    private final String literal;

    BotCommand(String literal) {
        this.literal = literal;
    }

    public static BotCommand findCommand(String text) {
        return Arrays.stream(values())
                .filter(command -> text.contains(command.literal))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown command: " + text));
    }
}
