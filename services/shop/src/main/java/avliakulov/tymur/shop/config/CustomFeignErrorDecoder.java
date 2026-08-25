package avliakulov.tymur.shop.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final org.slf4j.Logger log = LoggerFactory.getLogger(CustomFeignErrorDecoder.class);
    @Override
    public Exception decode(String methodKey, Response response) {
        String body = "{}";

        try {
            if (response.body() != null) {
                // Вычитываем тело ошибки (например, JSON с описанием 500 ошибки от внешнего сервиса)
                byte[] bodyBytes = Util.toByteArray(response.body().asInputStream());
                body = new String(bodyBytes, StandardCharsets.UTF_8);
            }

            // Кладем данные ошибки в MDC — Logback выведет это в JSON
            MDC.put("http_type", "response_error");
            MDC.put("http_status", String.valueOf(response.status()));
            MDC.put("http_url", response.request().url());
            MDC.put("http_method", response.request().httpMethod().name());
            MDC.put("http_body", body);

            log.error("Feign request failed with status {}", response.status());

        } catch (IOException e) {
            log.error("Failed to read response body on Feign error", e);
        } finally {
            // Очищаем MDC
            MDC.remove("http_type");
            MDC.remove("http_status");
            MDC.remove("http_url");
            MDC.remove("http_method");
            MDC.remove("http_body");
        }

        // Возвращаем стандартное исключение FeignException (или ваше кастомное)
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
