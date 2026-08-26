package avliakulov.tymur.shop.controller;

import avliakulov.tymur.dto.AccountDto;
import avliakulov.tymur.shop.feign.BankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Value("${app.environment}")
    private String environment;

    @Value("${app.greeting-message}")
    private String greetingMessage;

    @Value("${app.password}")
    private String password;

    @Autowired
    private BankClient bankClient;

    @GetMapping("/api")
    public Map<String, String> greeting() {
        return Map.of(
                "message", greetingMessage,
                "environment", environment,
                "password", password
        );
    }

    @GetMapping("/info")
    public AccountDto getInfo(@RequestParam Long accountId) {
        return bankClient.getBankData(accountId);
    }
}
