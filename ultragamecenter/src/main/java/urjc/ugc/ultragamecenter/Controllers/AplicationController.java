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

    


    @GetMapping("/add-table")
    public String addTable(@RequestParam String tableType, Model model) {
        Tablegame table = new Tablegame(tableType);
        trepository.save(table);
        return "reservation";
    }

    @GetMapping("/add-table-reservation")
    public String addTableReservation(@RequestParam Tablegame table, @RequestParam Date initialDate,
            @RequestParam Date endDate, String email, Model model) {
        String ReservationCode = "234567876";
        TableReservation tr = new TableReservation(table.getId(), ReservationCode, initialDate, endDate);
        if (!(email.equals("")) && EmailSenderService.isEmail.matcher(email).matches()) {
            EmailSenderService.sendEmail(email,
                    "Congratulations, this is your ReservationCode of one of our best tables, enjoy it bruh",
                    ReservationCode);
        }
        trrepository.save(tr);
        return "reservation";
    }

    @GetMapping("/add") // Map ONLY POST Requests
    public String add(Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Tablegame table = new Tablegame("PC");
        TableReservation tr = new TableReservation(table.getId(), "234567876", Date.from(Instant.now()),
                Date.from(Instant.now()));
        trrepository.save(tr);
        trepository.save(table);
        return "Save";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        Event e = eRepository.findByid(2);
        Tablegame t = trepository.findById(5);
        TableReservation tr = trrepository.findByid(4);
        model.addAttribute("a2", e.toString());
        model.addAttribute("a3", t.toString());
        model.addAttribute("a4", tr.toString());
        return "test";
    }

    


}