package urjc.ugc.ultragamecenter.Controllers;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
import urjc.ugc.ultragamecenter.Services.EmailSenderService;

@Controller
@SpringBootApplication
public class AplicationController {

    @Autowired
    EventRepository eRepository;

    @Autowired
    TableRepository trepository;

    @Autowired
    TableReservationRepository trrepository;

    

//------------------------------------------------------------------------------------------------------
    @GetMapping("/add-table")//ESTO HAY QUE REVISARLO
    public String addTable(@RequestParam String tableType,@RequestParam String date, Model model) {
        Tablegame table = new Tablegame(tableType,date);
        trepository.save(table);
        return "reservation";
    }

    @GetMapping("/newEmail")
    public String sendEmail(Model model){
        EmailSenderService.sendEmail("santo2927@gmail.com", "tonto", "mazo de tonto");
        return "reservation";

    }
//-------------------------------------------------------------------------------------------------------

    @GetMapping("/add-table-reservation")
    public String addTableReservation(@RequestParam Tablegame table, @RequestParam Date initialDate,
            @RequestParam Date endDate, String email, Model model) {
        String ReservationCode = "234567876";
        //TableReservation tr = new TableReservation(table.getId(), ReservationCode, initialDate, endDate);
        if (!(email.equals("")) && EmailSenderService.isEmail.matcher(email).matches()) {
            EmailSenderService.sendEmail(email,
                    "Congratulations, this is your ReservationCode of one of our best tables, enjoy it bruh",
                    ReservationCode);
        }
        //trrepository.save(tr);
        return "reservation";
    }

}