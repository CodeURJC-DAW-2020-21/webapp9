package urjc.ugc.ultragamecenter.repositories;
import urjc.ugc.ultragamecenter.models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TableRepository extends JpaRepository<Tablegame, Integer> {
    //Tablegame findById(long table_id);
    Tablegame findById(long id);
}
