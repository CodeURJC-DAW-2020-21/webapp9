package urjc.ugc.ultragamecenter.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urjc.ugc.ultragamecenter.models.Tablegame;
import urjc.ugc.ultragamecenter.repositories.TableRepository;

@Service
public class TableService {
    @Autowired
    TableRepository trepository;


    public Optional<Tablegame> getByid(Long id){
        return trepository.findById(id);
    }

    public List<Tablegame> getByTypeAndDate(String type, java.sql.Date date){
        return trepository.findByTypeAndDate(type, date);
    }

    public void saveAll(List<Tablegame> tables){
        trepository.saveAll(tables)
    }
}
