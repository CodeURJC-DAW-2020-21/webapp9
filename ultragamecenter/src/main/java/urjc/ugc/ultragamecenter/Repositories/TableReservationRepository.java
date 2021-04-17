package urjc.ugc.ultragamecenter.Repositories;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Integer> {
    TableReservation findByid(long id);
}
