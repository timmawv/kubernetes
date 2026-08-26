package avliakulov.tymur.bank.controller;

import avliakulov.tymur.bank.entity.Account;
import avliakulov.tymur.bank.service.AccountService;
import avliakulov.tymur.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    @Value("${app.environment}")
    private String environment;
    @Value("${app.greeting-message}")
    private String greetingMessage;
    @Value("${app.password}")
    private String password;

    private final AccountService accountService;

    @GetMapping("/api")
    public Map<String, String> greeting() {
        return Map.of(
                "message", greetingMessage,
                "environment", environment,
                "password", password
        );
    }

    @GetMapping("/data")
    public AccountDto getBankData(@RequestParam Long accountId) {
        return accountService.getAccountById(accountId);
    }
}
