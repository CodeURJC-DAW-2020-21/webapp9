package urjc.ugc.ultragamecenter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.repositories.TableReservationRepository;

@Service
public class TableReservationService {
    @Autowired
    private TableReservationRepository trrepository;


    public List<TableReservation> getAll(){
        return trrepository.findAll();
    }

    public TableReservation getByid(Long id){
        return trrepository.findByid(id);
    }

    public void delete(TableReservation reservation){
        trrepository.delete(reservation);
    }

    public void save(TableReservation reservation){
        trrepository.save(reservation);
    }
}
