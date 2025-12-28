package spaceraze.servlet.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.user.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String username);

}
