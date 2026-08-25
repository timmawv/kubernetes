package avliakulov.tymur.shop.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger customFeignLogger() {
        return new CustomFeignLogger();
    }

    @Bean
    public ErrorDecoder customErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;   // BASIC / HEADERS / FULL
    }
}
