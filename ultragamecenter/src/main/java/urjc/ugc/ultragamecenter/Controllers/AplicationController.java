package urjc.ugc.ultragamecenter.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpSession;
import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
import urjc.ugc.ultragamecenter.Services.EmailSenderService;
import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Types.TableType;
import org.springframework.http.HttpHeaders;
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

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

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
        model.addAttribute("Email", loggedUser.getLoggedUser().getEmail());
        model.addAttribute("Address", loggedUser.getLoggedUser().getAddress());
        model.addAttribute("Name", loggedUser.getLoggedUser().getName());
        model.addAttribute("Surname", loggedUser.getLoggedUser().getLastName());
        
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
        User user = new User("pepe", "pepemola", "Elez", "elpepe@gmail.com", "calle de la desesperacion", "friki");
        Tablegame table = new Tablegame(TableType.PC, false);
        TableReservation tr = new TableReservation(table.getId(), "234567876", Date.from(Instant.now()),
                Date.from(Instant.now()));
        eRepository.save(event);
        urepository.save(user);
        trrepository.save(tr);
        trepository.save(table);
        return "Save";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
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

    @GetMapping("/profile")
    public String getProfile(Model model) {
        if(this.loggedUser.getLoggedUser()!=null){
            return getUser(model);
        } else{
            return getLoginRegister(model);
        }
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        return "EditProfileTemplate";
    }

    @GetMapping("/register")
	public String getLoginRegister(Model model) {
		model.addAttribute("nombre4", "Register Page");
		return "LoginRegisterTemplate";
	}

    @PostMapping("/logginUser")
	public String logearUsuario(@RequestParam String email, @RequestParam String password, HttpSession sesion,Model model) {
		User aux = urepository.findByEmail(email);
		if (aux.getPassword().equals(password)) {
			this.loggedUser.setLoggedUser(aux);
		} 
		return getProfile(model);
	}
    @PostMapping("/editPassword")
	public String editPassword(@RequestParam String password, @RequestParam String password_repeated, @RequestParam String new_password, HttpSession sesion) {
		User aux = this.loggedUser.getLoggedUser();
		if (aux.getPassword().equals(password)&&password.equals(password_repeated)) {
			aux.setPassword(new_password);
		}
        urepository.save(aux); 
		return "EditProfileTemplate";
	}

    @PostMapping("/editProfile")
	public String editProfile(@RequestParam String name, @RequestParam String surname,@RequestParam String adress, @RequestParam MultipartFile image, HttpSession sesion) throws IOException {
		User aux = this.loggedUser.getLoggedUser();
        Files.createDirectories(IMAGES_FOLDER);
        Path imagePath = IMAGES_FOLDER.resolve("image"+aux.getEmail()+".jpg");
        image.transferTo(imagePath);
		aux.setAddress(adress);
        aux.setLastName(surname);
        aux.setName(name);
        urepository.save(aux);
		return "EditProfileTemplate";
	}
    @GetMapping("/get-user-image")	
	public ResponseEntity<Object> downloadImage(Model model) throws MalformedURLException {
        User aux = this.loggedUser.getLoggedUser();
		Path imagePath = IMAGES_FOLDER.resolve("image"+aux.getEmail()+".jpg");
		
		Resource image = new UrlResource(imagePath.toUri());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);		
	}
}