package urjc.ugc.ultragamecenter;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByid(long id);
}