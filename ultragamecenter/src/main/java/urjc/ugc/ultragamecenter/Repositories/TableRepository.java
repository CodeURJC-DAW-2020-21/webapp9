package urjc.ugc.ultragamecenter.repositories;
import urjc.ugc.ultragamecenter.models.*;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Tablegame, Long> {

    Tablegame findById(long id);

    List<Tablegame> findByType(String type);
    List<Tablegame> findByTypeAndDate(String t,Date d);

}
