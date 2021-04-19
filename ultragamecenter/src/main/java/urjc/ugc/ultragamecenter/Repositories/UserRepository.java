package urjc.ugc.ultragamecenter.repositories;
import urjc.ugc.ultragamecenter.models.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findById(Long id);

}
