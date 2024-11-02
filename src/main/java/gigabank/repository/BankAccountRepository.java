package gigabank.repository;

import gigabank.entity.BankAccount;

import java.util.List;

public interface BankAccountRepository {
    List<BankAccount> findAll();

    BankAccount findById(Long id);

    Long save(BankAccount bankAccount);

    void updateById(BankAccount bankAccount);

    void deleteById(Long id);
}
