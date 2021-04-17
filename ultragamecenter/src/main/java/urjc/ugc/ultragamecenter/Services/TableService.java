package urjc.ugc.ultragamecenter.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urjc.ugc.ultragamecenter.Models.Tablegame;
import urjc.ugc.ultragamecenter.Repositories.TableRepository;

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
        trepository.saveAll(tables);
    }

    public List<Tablegame> getAll(){
        return trepository.findAll();
    }

    public void deleteById(Long id){
        trepository.deleteById(id);
    }
}
