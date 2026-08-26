package avliakulov.tymur.shop.feign;

import avliakulov.tymur.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bank-service", url = "${clients.bank-url}")
public interface BankClient {

    @GetMapping("/bank/account")
    AccountDto getBankData(@RequestParam Long accountId);
}
