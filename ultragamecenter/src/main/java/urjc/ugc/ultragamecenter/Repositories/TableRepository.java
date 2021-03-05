package urjc.ugc.ultragamecenter.Repositories;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Tablegame, Integer> {
    Tablegame findByid(long id);
}
