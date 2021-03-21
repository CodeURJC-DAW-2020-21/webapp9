package urjc.ugc.ultragamecenter.repositories;
import urjc.ugc.ultragamecenter.models.*;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(long id);
    User findByEmail(String email);
}