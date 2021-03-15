package urjc.ugc.ultragamecenter.Controllers;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
import urjc.ugc.ultragamecenter.Types.*;

import urjc.ugc.ultragamecenter.Components.UserComponent;

@Controller
public class UserController {
	@Autowired
	private UserRepository urepository;
	@Autowired
	UserComponent loggedUser;

	@Autowired
	EventRepository eRepository;

	@Autowired
	TableRepository trepository;

	@Autowired
	TableReservationRepository trrepository;

	private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

	@GetMapping("/")
	public String getIndex(Model model) {

		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("nombre", "Index");
		return "IndexTemplate";
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {

		if (this.loggedUser.isAdmin()) {
			model.addAttribute("Admin", "Administrador");
			model.addAttribute("Logout", "Cerrar sesión");
			model.addAttribute("nombre", "Admin");
			model.addAttribute("events", eRepository.findAll());
			model.addAttribute("reservations", trrepository.findAll());

			return "AdminTemplate";
		} else {
			return getProfile(model);
		}

	}

	@GetMapping("/reservation")
	public String getReservation(Model model) {

		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("nombre5", "Reservation Page");
		return "ReservationTemplate";
	}

	@GetMapping("/singleevent")
	public String getSingleEvent(Model model) {

		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("nombre6", "SingleEvent Page");
		return "SingleEventTemplate";

	}

	@GetMapping("/events")
	public String getEvents(Model model) {
		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("nombre", "Events Page");
		model.addAttribute("events", eRepository.findAll());
		return "EventsTemplate";
	}

	@GetMapping("/admin/graph-event")
	public String graphEvent(@RequestParam String id, Model model) {
		Event event = eRepository.findByid(Long.parseLong(id));
		Integer likes = event.getlikes();
		model.addAttribute("likes", likes);
		Integer plazasLibres = event.getCapacity() - likes;
		model.addAttribute("plazasLibres", plazasLibres);
		return "GraphsEventsTemplate";
	}

	@GetMapping("/events/see-event")
	public String seeEvent(@RequestParam String id, Model model) {
		Event event = eRepository.findByid(Long.parseLong(id));

		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("image", event.getBannerUrl());
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		model.addAttribute("date", event.getDate());
		model.addAttribute("capacity", event.getCapacity() - event.getlikes());
		model.addAttribute("id", event.getId());
		return "SingleEventTemplate";
	}

	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model) {

		Event event = eRepository.findByid(Long.parseLong(id));
		if (this.loggedUser.isLoggedUser() && !this.loggedUser.hasLiked(event)) {
			this.loggedUser.like(event);
			eRepository.save(event);
		} else{
			return getProfile(model);
		}

		return getEvents(model);
	}

	@GetMapping("/admin/graph-tables")
	public String graphTables(Model model) {
		List<TableReservation> reservations = trrepository.findAll();

		Integer numPC = 0;
		Integer numXBOX_ONE = 0;
		Integer numPS5 = 0;

		for (TableReservation tableReservation : reservations) {
			Tablegame table = trepository.findByid(tableReservation.getId_table());

			switch (table.getType()) {
			case PC:
				numPC++;
				break;
			case XBOX_ONE:
				numXBOX_ONE++;
				break;
			case PS5:
				numPS5++;
				break;
			}
		}
		model.addAttribute("numPC", numPC);
		model.addAttribute("numXBOX_ONE", numXBOX_ONE);
		model.addAttribute("numPS5", numPS5);
		return "GrapsTablesTemplate";
	}

	@GetMapping("/admin/delete-reservation")
	public String borrarReserva(@RequestParam String id, Model model) {
		TableReservation reserva = trrepository.findByid(Long.parseLong(id));
		trrepository.delete(reserva);
		model.addAttribute("events", eRepository.findAll());
		model.addAttribute("reservations", trrepository.findAll());
		return getAdmin(model);
	}

	@GetMapping("/admin/delete-event")
	public String borrarEvento(@RequestParam String id, Model model) {
		Event evento = eRepository.findByid(Long.parseLong(id));
		if (evento != null) {
			eRepository.delete(evento);
		}
		model.addAttribute("events", eRepository.findAll());
		model.addAttribute("reservations", trrepository.findAll());
		return getAdmin(model);
	}

	@GetMapping("/Event-Adder")
	public String getEventAdder(Model model) {
		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		if (this.loggedUser.isAdmin()) {
			return "EventCreatorTemplate";
		}
		return getProfile(model);
	}

	@GetMapping("/user")
	public String getUser(Model model) {
		if (this.loggedUser.isLoggedUser()) {
			model.addAttribute("Logout", "Cerrar sesión");
			model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
			model.addAttribute("nombre3", "User Page");
			model.addAttribute("events", loggedUser.getLoggedUser().getEvents());
			model.addAttribute("tables", loggedUser.getLoggedUser().getTables());
			model.addAttribute("Email", loggedUser.getLoggedUser().getEmail());
			model.addAttribute("Name", loggedUser.getLoggedUser().getName());
			model.addAttribute("Surname", loggedUser.getLoggedUser().getLastName());
			return "UserTemplate";
		} else {
			return getProfile(model);
		}
	}

	@GetMapping("/profile")
	public String getProfile(Model model) {
		return this.loggedUser.isLoggedUser() ? getUser(model) : getLoginRegister(model);
	}

	@GetMapping("/get-user-image")
	public ResponseEntity<Object> downloadImage(Model model) throws MalformedURLException {
		User aux = this.loggedUser.getLoggedUser();
		Path imagePath = IMAGES_FOLDER.resolve("image" + aux.getEmail() + ".jpg");

		Resource image = new UrlResource(imagePath.toUri());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);
	}

	@GetMapping("/edit-profile")
	public String editProfile(Model model) {
		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");

		String name = loggedUser.getLoggedUser().getName();
		String surname = loggedUser.getLoggedUser().getLastName();
		model.addAttribute("Name", name.equals("") ? "Nombre*" : name);
		model.addAttribute("Surname", name.equals("") ? "Apellidos*" : surname);

		return "EditProfileTemplate";
	}

	@GetMapping("/register")
	public String getLoginRegister(Model model) {

		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("nombre4", "Register Page");
		model.addAttribute("Registered", "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		return "LoginRegisterTemplate";
	}

	@GetMapping("/loggout")
	public String LoggOut(HttpSession sesion, Model model) {
		this.loggedUser.logOut();
		return getProfile(model);
	}

	@PostMapping("/logginUser")
	public String logearUsuario(@RequestParam String email, @RequestParam String password, HttpSession sesion,
			Model model) {
		User aux = urepository.findByEmail(email);
		if (aux != null) {
			if (aux.getPassword().equals(password)) {
				this.loggedUser.setLoggedUser(aux);
			} else {
				model.addAttribute("Registered", "La contraseña va a ser que no es");
			}
		} else {
			model.addAttribute("Registered", "Esa cuenta no existe");
		}
		return getProfile(model);
	}

	@PostMapping("/editPassword")
	public String editPassword(@RequestParam String password, @RequestParam String password_repeated,
			@RequestParam String new_password, HttpSession sesion) {
		User aux = this.loggedUser.getLoggedUser();
		if (aux.getPassword().equals(password) && password.equals(password_repeated)) {
			aux.setPassword(new_password);
		}
		urepository.save(aux);
		return "EditProfileTemplate";
	}

	@PostMapping("/editProfile")
	public String editProfile(@RequestParam String name, @RequestParam String surname,
			@RequestParam MultipartFile image, HttpSession sesion) throws IOException {
		User aux = this.loggedUser.getLoggedUser();
		Files.createDirectories(IMAGES_FOLDER);
		Path imagePath = IMAGES_FOLDER.resolve("image" + aux.getEmail() + ".jpg");
		image.transferTo(imagePath);
		if (!surname.equals("")) {
			aux.setLastName(surname);
		}
		if (!name.equals("")) {
			aux.setName(name);
		}
		urepository.save(aux);
		return "EditProfileTemplate";
	}

	@PostMapping("/registerUser")
	public String registrarUsuario(@RequestParam String name, @RequestParam String lastName, @RequestParam String email,
			@RequestParam String password, HttpSession sesion, Model model) {
		User aux = urepository.findByEmail(email);
		if (aux != null) {
			model.addAttribute("Registered", "Ya hay un usuario registrado con ese correo");
		} else {
			User user = new User(name, lastName, password, email);
			urepository.save(user);
			model.addAttribute("Registered", "Te has registrado correctamente :D");
		}
		return getLoginRegister(model);
	}

	@PostMapping("/createEvent")
	public String registrarUsuario(@RequestParam String name, @RequestParam String description,
			@RequestParam Integer capacity, @RequestParam String labels, @RequestParam String end, HttpSession sesion,
			Model model) throws ParseException {
		Event event = new Event(name, description, end, "", capacity);

		eRepository.save(event);
		return getAdmin(model);
	}

}
