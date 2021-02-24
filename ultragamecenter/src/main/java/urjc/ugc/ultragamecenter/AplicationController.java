package urjc.ugc.ultragamecenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AplicationController{
    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("nombre","Index");
        return "IndexTemplate";
    }

    @GetMapping("/Admin")
    public String getAdmin(Model model){
        model.addAttribute("nombre","Admin");
        return "AdminTemplate";
    }

    @GetMapping("/User")
    public String getUser(Model model){
        model.addAttribute("nombre","User Page");
        return "UserTemplate";
    }

    @GetMapping("/Register")
    public String getLoginRegister(Model model){
        model.addAttribute("nombre","User Page");
        return "LoginRegisterTemplate";
    }

    @GetMapping("/Reservation")
    public String getReservation(Model model){
        model.addAttribute("nombre","User Page");
        return "ReservationTemplate";
    }

    @GetMapping("/SingleEvent")
    public String getSingleEvent(Model model){
        model.addAttribute("nombre","User Page");
        return "SingleEventTemplate";
    }

    @GetMapping("/Events")
    public String getEvents(Model model){
        model.addAttribute("nombre","User Page");
        return "EventsTemplate";
    }


}