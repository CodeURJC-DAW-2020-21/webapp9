package urjc.ugc.ultragamecenter.repositories;
import urjc.ugc.ultragamecenter.models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Integer> {
    TableReservation findByid(long id);
}
