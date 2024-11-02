package gigabank.repository;

import gigabank.entity.User;
import gigabank.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Получает из базы данных всех пользователей с их банковскими счетами
     */
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM app_user", new UserMapper());
    }

    /**
     * Получает из базы данных пользователя по id с его банковскими счетами
     */
    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM app_user WHERE app_user.user_id = ?",
                new UserMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public Long save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO app_user (first_name, middle_name, last_name, phone, birthdate) " +
                            "VALUES (?, ?, ?, ?, ?) RETURNING user_id",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPhone());
            ps.setDate(5, Date.valueOf(user.getBirthDate()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateById(User user) {
        jdbcTemplate.update("UPDATE app_user SET first_name=?, middle_name=?, last_name=?, phone=?, birthdate=? " +
                        "WHERE user_id =?",
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getPhone(),
                user.getBirthDate(),
                user.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM app_user WHERE user_id=?", id);
    }}