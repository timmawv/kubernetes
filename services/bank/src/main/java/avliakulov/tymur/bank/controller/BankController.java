package avliakulov.tymur.bank.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Value("${app.environment}")
    private String environment;

    @Value("${app.greeting-message}")
    private String greetingMessage;

    @Value("${app.password}")
    private String password;

    @GetMapping("/api")
    public Map<String, String> greeting() {
        return Map.of(
                "message", greetingMessage,
                "environment", environment,
                "password", password
        );
    }

    @GetMapping("/data")
    public Map<String, String> getBankData() {
        return Map.of(
                "id", "20",
                "balance", "1000"
        );
    }
}
