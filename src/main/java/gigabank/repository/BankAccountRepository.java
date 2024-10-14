package gigabank.repository;

import gigabank.entity.BankAccount;
import gigabank.entity.User;

import java.util.List;

public interface BankAccountRepository {
    List<BankAccount> findAll();

    BankAccount findById(long id);

    long save(BankAccount bankAccount);

    void updateById(BankAccount bankAccount);

    void deleteById(long id);
}
