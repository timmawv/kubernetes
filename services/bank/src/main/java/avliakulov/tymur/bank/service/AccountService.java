package avliakulov.tymur.bank.service;

import avliakulov.tymur.bank.entity.Account;
import avliakulov.tymur.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }
}
