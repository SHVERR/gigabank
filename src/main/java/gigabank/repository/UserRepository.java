package gigabank.repository;

import gigabank.entity.User;

import java.util.List;

public interface UserRepository {
    /**
     * Получает из базы данных всех пользователей с их банковскими счетами
     */
    List<User> findAll();

    /** Получает из базы данных пользователя по id с его банковскими счетами */
    User findById(Long id);

    Long save(User user);

    void updateById(User user);

    void deleteById(Long id);
}