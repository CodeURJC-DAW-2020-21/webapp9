package urjc.ugc.ultragamecenter.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;

@Controller

public class ReservationController {

    @Autowired
    TableService tService;

    @Autowired
    TableReservationService trService;

    @Autowired
    UserService uService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("Admin", uDetails.isLoggedUserADMIN() ? "Admin" : "");
		model.addAttribute("Logout",  uDetails.idLoggedUser() ? "Log Out" : "" );
		model.addAttribute("Admin-ico", uDetails.isLoggedUserADMIN() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", uDetails.idLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

    @GetMapping("/reservation")
    public String getReservation(Model model, HttpServletRequest request) {
        setHeader(model);
        model.addAttribute("site", "MESAS");
        model.addAttribute("full", "");
        model.addAttribute("logged", !uDetails.idLoggedUser());
        return "ReservationTemplate";
    }

    @PostMapping("/trytoreserve")
    public String reserve(@RequestParam String type, @RequestParam String day, @RequestParam String hour,
            @RequestParam(required = false) String email, Model model, HttpServletRequest request) {
        setHeader(model);
        Integer hourInt = Integer.parseInt(hour);
        Object[] o = trService.getReserved(hourInt, type, day);
        boolean reserved = (Boolean) o[0];
        Long tableId = (Long) o[1];
        if (!reserved) {// not reserved
            String full = "No hay disponibilidad de mesas de " + type + " para el dia " + day
                    + " en la hora seleccionada";
            model.addAttribute("full", full);
            model.addAttribute("site", "MESAS");
            return "ReservationTemplate";
        } else {// reserved
            trService.reserve(email, tableId, hourInt);
        }
        return getReservation(model,request);
    }
}
