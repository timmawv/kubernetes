package avliakulov.tymur.shop.config;

import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomFeignLogger extends feign.Logger {

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        // Достаем тело запроса, если оно есть
        String body = request.body() != null
                ? new String(request.body(), StandardCharsets.UTF_8)
                : "{}";

        // Форматируем в ОДНО сообщение
        log.info("Feign Outgoing Request  --> [{}] {} | Body: {}",
                request.httpMethod(), request.url(), body);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsed) throws IOException {
        String body = "{}";
        Response responseToReturn = response;

        // Если есть тело ответа — вычитываем его и пересоздаем поток
        if (response.body() != null && response.body().length() != null) {
            byte[] bodyBytes = Util.toByteArray(response.body().asInputStream());
            body = new String(bodyBytes, StandardCharsets.UTF_8);

            // Восстанавливаем тело ответа для бизнес-логики
            responseToReturn = response.toBuilder().body(bodyBytes).build();
        }

        // Форматируем ответ в ОДНО сообщение
        log.info("Feign Incoming Response <-- [{}] Status: {} | Time: {}ms | Body: {}",
                response.request().httpMethod(), response.status(), elapsed, body);

        return responseToReturn;
    }


    @Override
    protected void log(String s, String s1, Object... objects) {
        // Оставляем пустым, чтобы Feign не выводил свои стандартные промежуточные строки
    }
}
