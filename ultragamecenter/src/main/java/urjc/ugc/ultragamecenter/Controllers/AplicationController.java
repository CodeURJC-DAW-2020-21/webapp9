package urjc.ugc.ultragamecenter.Controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
import urjc.ugc.ultragamecenter.Services.EmailSenderService;
import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Types.TableType;

import urjc.ugc.ultragamecenter.Components.*;

@Controller
public class AplicationController {

    @Autowired
    EventRepository eRepository;

    @Autowired
    UserRepository urepository;

    @Autowired
    TableRepository trepository;

    @Autowired
    TableReservationRepository trrepository;

    @Autowired
    UserComponent loggedUser;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("nombre", "Index");
        return "IndexTemplate";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("nombre", "Admin");
        model.addAttribute("events", eRepository.findAll());
        model.addAttribute("reservations", trrepository.findAll());
        return "AdminTemplate";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        model.addAttribute("nombre3", "User Page");
        model.addAttribute("events", loggedUser.getLoggedUser().getEvents());
        model.addAttribute("tables", loggedUser.getLoggedUser().getTables());
        return "UserTemplate";
    }

    @GetMapping("/reservation")
    public String getReservation(Model model) {
        model.addAttribute("nombre5", "Reservation Page");
        return "ReservationTemplate";
    }

    @GetMapping("/singleevent")
    public String getSingleEvent(Model model) {
        model.addAttribute("nombre6", "SingleEvent Page");
        return "SingleEventTemplate";
    }

    @GetMapping("/events")
    public String getEvents(Model model) {
        model.addAttribute("nombre", "Events Page");
        model.addAttribute("events", eRepository.findAll());
        return "EventsTemplate";
    }

    @GetMapping("/add-event")
    public String addEvent(@RequestParam String name, @RequestParam String description, @RequestParam String date,
            @RequestParam String bannerUrl, Model model, @RequestParam EventLavelType... lavels) {
        Event event = new Event(name, description, date, bannerUrl);
        for (EventLavelType var : lavels) {
            event.putLavel(var);
        }
        eRepository.save(event);
        return "events";
    }

    @GetMapping("/add-table")
    public String addTable(@RequestParam TableType tableType, Model model) {
        Tablegame table = new Tablegame(tableType, false);
        trepository.save(table);
        return "reservation";
    }

    @GetMapping("/add-table-reservation")
    public String addTableReservation(@RequestParam Tablegame table, @RequestParam Date initialDate,
            @RequestParam Date endDate, String email, Model model) {
        // Coger de la base de datos una identificación que no esté(por ejemplo la más
        // mayor +1)
        // y ponerla
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
        Event event = new Event("Fornite", "fornite x marvel", "2015-03-15", "Hola");
        event.putLavel(EventLavelType.MOBA);
        event.putLavel(EventLavelType.SHOOTER);
        User user = new User("pepe", "pepemola", "Elez", "elpepe@gmail.com","calle de la desesperacion", "friki");
        Tablegame table = new Tablegame(TableType.PC, false);
        TableReservation tr = new TableReservation(table.getId(), "234567876", Date.from(Instant.now()), Date.from(Instant.now()));
        // eventItem event = new eventItem();
        // event.setName("Fornite");
        // event.setDesc("fornite x marvel");
        eRepository.save(event);
        urepository.save(user);
        trrepository.save(tr);
        trepository.save(table);
        return "Save";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        // List<Event> lista;
        // lista = eventRepository.findAll();
        Event e = eRepository.findByid(2);
        User u = urepository.findByid(3);
        Tablegame t = trepository.findByid(5);
        TableReservation tr = trrepository.findByid(4);
        model.addAttribute("a1", u.toString());
        model.addAttribute("a2", e.toString());
        model.addAttribute("a3", t.toString());
        model.addAttribute("a4", tr.toString());
        return "test";
    }

    @GetMapping("/borrar-reserva")
    public String borrarReserva(@RequestParam String id, Model model){
        TableReservation reserva = trrepository.findByid(Long.parseLong(id));
        trrepository.delete(reserva);
        model.addAttribute("events", eRepository.findAll());
        model.addAttribute("reservations", trrepository.findAll());
        return "AdminTemplate";
    }

    @GetMapping("/borrar-evento")
    public String borrarEvento(@RequestParam String id, Model model){
        Event evento = eRepository.findByid(Long.parseLong(id));
        eRepository.delete(evento);
        model.addAttribute("events", eRepository.findAll());
        model.addAttribute("reservations", trrepository.findAll());
        return "AdminTemplate";
    }
}