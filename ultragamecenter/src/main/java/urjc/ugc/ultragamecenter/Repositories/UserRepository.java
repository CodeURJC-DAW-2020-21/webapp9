package urjc.ugc.ultragamecenter.Repositories;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(long id);
    User findByEmail(String email);
}