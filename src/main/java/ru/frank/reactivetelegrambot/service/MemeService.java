package ru.frank.reactivetelegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import ru.frank.reactivetelegrambot.gateway.MemeClient;

import java.io.File;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class MemeService {
    private final MemeClient client;

    @SneakyThrows
    public File loadMeme() {
        var response = client.getMeme();
        var file = new File("stubfilename");
        FileUtils.copyURLToFile(
                new URL(response.getUrl()),
                file
        );

        return file;
    }
}
