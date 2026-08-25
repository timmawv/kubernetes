package avliakulov.tymur.shop.config;

import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomFeignLogger extends feign.Logger {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(CustomFeignLogger.class);

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        try {
            String body = request.body() != null
                    ? new String(request.body(), StandardCharsets.UTF_8)
                    : "{}";

            // Кладем структурированные данные прямо в MDC
            MDC.put("http_type", "request");
            MDC.put("http_method", request.httpMethod().name());
            MDC.put("http_url", request.url());
            MDC.put("http_body", body);

            // Сам текст сообщения может быть кратким
            log.info("Feign outgoing request");
        } finally {
            // Очищаем кастомные ключи, чтобы они не утекли в другие логи текущего потока
            MDC.remove("http_type");
            MDC.remove("http_method");
            MDC.remove("http_url");
            MDC.remove("http_body");
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String body = "{}";
        Response responseToReturn = response;

        if (response.body() != null && response.body().length() != null) {
            byte[] bodyBytes = Util.toByteArray(response.body().asInputStream());
            body = new String(bodyBytes, StandardCharsets.UTF_8);
            responseToReturn = response.toBuilder().body(bodyBytes).build();
        }

        try {
            MDC.put("http_type", "response");
            MDC.put("http_status", String.valueOf(response.status()));
            MDC.put("execution_time_ms", String.valueOf(elapsedTime));
            MDC.put("http_body", body);

            log.info("Feign incoming response");
        } finally {
            MDC.remove("http_type");
            MDC.remove("http_status");
            MDC.remove("execution_time_ms");
            MDC.remove("http_body");
        }

        return responseToReturn;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        // Оставляем пустым, так как мы полностью переопределили logRequest и logAndRebufferResponse
    }
}
