package ru.frank.reactivetelegrambot.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frank.reactivetelegrambot.dto.GetMemeResponse;

@FeignClient(
        name = "MemeClient",
        url = "${meme.gateway.url}"
)
public interface MemeClient {
    @GetMapping(value = "/gimme", produces = MediaType.APPLICATION_JSON_VALUE)
    GetMemeResponse getMeme();
}
