package urjc.ugc.ultragamecenter.controllers;

import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.components.*;

import org.springframework.context.ApplicationContext;

@Controller

public class ReservationController {
    

    @Autowired
    UserComponent userComponent;

    @Autowired
    TableService tService;

    @Autowired
    TableReservationService trService;

    @Autowired
    UserService uService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/reservation")
    public String getReservation(Model model) {

        model.addAttribute("site", "MESAS");
        model.addAttribute("full", "");
        setHeader(model);
        model.addAttribute("logged", !this.userComponent.isLoggedUser());
        return "ReservationTemplate";
    }

    @PostMapping("/trytoreserve")
    public String reserve(@RequestParam String type, @RequestParam String day, @RequestParam String hour,
            @RequestParam(required = false) String email, Model model) throws MessagingException {
        Integer hour_int = Integer.parseInt(hour);
        Object[] o = trService.getReserved(hour_int,type,day);
        Boolean reserved = (Boolean) o[0];
        Long table_id = (Long) o[1];
        if (!reserved) {// not reserved
            String full = "No hay disponibilidad de mesas de " + type + " para el dia " + day
                    + " en la hora seleccionada";
            model.addAttribute("full", full);
            setHeader(model);
            model.addAttribute("site", "MESAS");
            return "ReservationTemplate";
        } else {// reserved
            trService.reserve(email,table_id,hour_int);
        }
        return getReservation(model);
    }

    // Util functions

    public void setHeader(Model model) {
        model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
        model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
        model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
        model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
    }
}
