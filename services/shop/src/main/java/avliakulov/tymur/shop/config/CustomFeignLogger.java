package avliakulov.tymur.shop.config;

import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CustomFeignLogger extends feign.Logger {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(CustomFeignLogger.class);

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        String body = request.body() != null ? new String(request.body(), request.charset()) : "{}";

        log.info("type: request, method: {}, url: {}, body: {}",
                request.httpMethod(),
                request.url(),
                body.isEmpty() ? "{}" : body);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String body = "{}";

        if (response.body() != null && response.status() != 204) {
            byte[] bodyBytes = Util.toByteArray(response.body().asInputStream());
            body = new String(bodyBytes, Util.UTF_8);

            // Обязательно пересоздаем response, так как поток тела можно прочитать только один раз
            response = response.toBuilder().body(bodyBytes).build();
        }

        log.info("type: response, status: {}, url: {}, elapsedTime: {}ms, body: {}",
                response.status(),
                response.request().url(),
                elapsedTime,
                body.isEmpty() ? "{}" : body);

        return response;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        // Оставляем пустым, так как мы полностью переопределили logRequest и logAndRebufferResponse
    }
}
