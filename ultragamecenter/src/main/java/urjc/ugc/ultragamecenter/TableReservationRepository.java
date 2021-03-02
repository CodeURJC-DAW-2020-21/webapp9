package urjc.ugc.ultragamecenter;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TableReservationRepository extends JpaRepository<TableReservation, Integer> {
    TableReservation findByid(long id);
}
