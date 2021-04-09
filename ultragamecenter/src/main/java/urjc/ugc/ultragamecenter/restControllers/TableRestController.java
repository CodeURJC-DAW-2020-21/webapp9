package urjc.ugc.ultragamecenter.restControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.api_models.API_table;
import urjc.ugc.ultragamecenter.api_models.API_tables;
import urjc.ugc.ultragamecenter.models.Tablegame;
import urjc.ugc.ultragamecenter.services.TableService;

@RestController
public class TableRestController {
    
    @Autowired
    TableService tService;

    @GetMapping("api/tables")
    public List<API_tables> all(){
        return API_tables.transform(tService.getAll());
    }

    @GetMapping("api/seeTable")
    public API_table getTable(@RequestParam Long id){
        return new API_table(tService.getByid(id).get());
    }

    @DeleteMapping("api/deleteTables")
    public void deleteTable(@RequestParam Long id){
        tService.deleteById(id);
    }

    @PostMapping("api/createTable")
    public ResponseEntity<String> createTable(){
        return null;
    }

    @PutMapping("api/editTables")
    public Tablegame editTable(){
        return null;
    }

}