package urjc.ugc.ultragamecenter.Repositories;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TableReservationRepository extends JpaRepository<TableReservation, Integer> {
    TableReservation findByid(long id);
}
