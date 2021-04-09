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
    private ApplicationContext appContext;

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

    public Object[] getReserved(Integer hour_int, String type, String day) {
        Long table_id = 0L;
        java.sql.Date sqldate = java.sql.Date.valueOf(day);
        Object[] o = new Object[2];
        List<Tablegame> tables = tService.getByTypeAndDate(type, sqldate);
        Boolean reserved = false;
        int i = 0;
        while (!reserved && (i != tables.size())) {
            if (tables.get(i).getState().get(hour_int) == 0) {
                tables.get(i).setState(hour_int, 1);
                table_id = tables.get(i).getId();
                tService.saveAll(tables);
                reserved = true;
            }
            i++;
        }
        o[0] = reserved;
        o[1] = table_id;
        return o;
    }

    @PostMapping("/trytoreserve")
    public String reserve(@RequestParam String type, @RequestParam String day, @RequestParam String hour,
            @RequestParam(required = false) String email, Model model) throws MessagingException {
        Integer hour_int = Integer.parseInt(hour);
        Object[] o = getReserved(hour_int, type, day);
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
            reserve(email,table_id,hour_int);
        }
        return getReservation(model);
    }

    // Util functions
    private String randomRefCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public void setHeader(Model model) {
        model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
        model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
        model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
        model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
    }

    public void sendMail(String to, String code) throws MessagingException {
        EmailSenderService emailSender = (EmailSenderService) appContext.getBean("emailSenderService");
        emailSender.sendEmail(to, code);
    }
    
    public TableReservation reserveTable(String type, String day, String hour, String email) throws MessagingException {
        Integer hour_int = Integer.parseInt(hour);
        Object[] o = getReserved(hour_int, type, day);
        Boolean reserved = (Boolean) o[0];
        Long table_id = (Long) o[1];
        if (reserved) {
            return reserve(email,table_id,hour_int);
        }
        return null;
    }

    public TableReservation reserve(String email, Long table_id, Integer hour_int) throws MessagingException{
        TableReservation tReserve=null;
        String randomCode = randomRefCode();
        if (this.userComponent.isLoggedUser()) {// logged user
            this.userComponent.getLoggedUser().addReferencedCode(randomCode);
            uService.save(this.userComponent.getLoggedUser());
            tReserve = new TableReservation(table_id, randomCode, hour_int);
            trService.save(tReserve);
        } else { // guest user
            if (!email.equals("")) {
                tReserve = new TableReservation(table_id, randomCode, hour_int);
                trService.save(tReserve);
                this.sendMail(email, randomCode);
            }
        }
        return tReserve;
    }
}
