package avliakulov.tymur.shop.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "bank-service", url = "${clients.bank-url}")
public interface BankClient {

    @GetMapping("/bank/data")
    Map<String, String> getBankData();
}
