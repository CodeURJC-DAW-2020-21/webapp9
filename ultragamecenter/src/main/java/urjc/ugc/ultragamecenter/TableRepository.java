package urjc.ugc.ultragamecenter;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Tablegame, Integer> {
    Tablegame findByid(long id);
}
