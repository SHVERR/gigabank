package gigabank.repository;

import gigabank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String username);

    List<User> findByFirstNameOrderByBirthDate(String firstName);
}