package avliakulov.tymur.bank.service;

import avliakulov.tymur.bank.entity.Account;
import avliakulov.tymur.bank.repository.AccountRepository;
import avliakulov.tymur.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void saveAccount(AccountDto accountDto) {
        accountRepository.save(new Account(accountDto.getId(), accountDto.getBalance()));
    }

    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return new AccountDto(account.getId(), account.getBalance());
    }

    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDto(account.getId(), account.getBalance()))
                .toList();
    }
}
