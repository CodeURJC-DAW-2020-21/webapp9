package urjc.ugc.ultragamecenter.Controllers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
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
        model.addAttribute("nombre2", "Admin");
        return "AdminTemplate";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        model.addAttribute("nombre3", "User Page");
        model.addAttribute("events",loggedUser.getLoggedUser().getEvents());
        model.addAttribute("tables",loggedUser.getLoggedUser().getTables());
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
        model.addAttribute("events",eRepository.findAll());
        return "EventsTemplate";
    }

    @GetMapping("/add") // Map ONLY POST Requests
    public String add(Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Event event = new Event("Fornite","fornite x marvel",2);
        User user = new User("pepe", "pepemola", "Elez", "elpepe@gmail.com", "friki");
        Tablegame table = new Tablegame("PC", false);
        Date objDate = new Date();
        TableReservation tr = new TableReservation(table.getId(), "234567876", objDate,objDate);
        //eventItem event = new eventItem();
        //event.setName("Fornite");
        //event.setDesc("fornite x marvel");
        eRepository.save(event);
        urepository.save(user);
        trrepository.save(tr);
        trepository.save(table);
        return "Save";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        //List<Event> lista;
        //lista = eventRepository.findAll();
        Event e = eRepository.findByid(2);
        User u = urepository.findByid(3);
        Tablegame t = trepository.findByid(5);
        TableReservation tr = trrepository.findByid(4);
        model.addAttribute("a1",u.toString());
        model.addAttribute("a2",e.toString());
        model.addAttribute("a3",t.toString());
        model.addAttribute("a4",tr.toString());
        return "test";
    }
}