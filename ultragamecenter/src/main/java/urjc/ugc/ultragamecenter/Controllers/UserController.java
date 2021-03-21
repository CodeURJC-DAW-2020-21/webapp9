package urjc.ugc.ultragamecenter.Controllers;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
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
import urjc.ugc.ultragamecenter.Services.EventService;
import urjc.ugc.ultragamecenter.Services.ImageService;
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

	@Autowired
	private EventService eventService;

	@Autowired
	private ImageService imageService;

	private Event editedEvent = null;

	private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";

	public void setHeader(Model model) {
		model.addAttribute("Logout", this.loggedUser.isLoggedUser() ? "Cerrar sesión" : "");
		model.addAttribute("Logout-ico", this.loggedUser.isLoggedUser() ? "fa fa-sign-out" : "");
		model.addAttribute("Admin", this.loggedUser.isAdmin() ? "Administrador" : "");
		model.addAttribute("Admin-ico", this.loggedUser.isAdmin() ? "fa fa-star" : "");

	}

	@GetMapping("/")
	public String getIndex(Model model) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "IndexTemplate";
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("site", "ADMIN");
		setHeader(model);
		if (this.loggedUser.isAdmin()) {
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
		model.addAttribute("site", "MESAS");
		setHeader(model);
		return "ReservationTemplate";
	}

	@GetMapping("/singleevent")
	public String getSingleEvent(Model model) {
		model.addAttribute("site", "EVENTO");
		setHeader(model);
		return "SingleEventTemplate";

	}

	@GetMapping("/events")
	public String getEvents(Model model) {
		setHeader(model);
		model.addAttribute("site", "EVENTOS");
		model.addAttribute("events", this.loggedUser.sort(eRepository.findAll()));
		return "EventsTemplate";
	}
	//

	@GetMapping("/admin/event-edit")
	public String editEvent(@RequestParam String id, Model model) {
		setHeader(model);
		Event event = eRepository.findByid(Long.parseLong(id));
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		String label = "";
		for (String x : event.getLavels()) {
			label += x;
			label += "/";
		}
		model.addAttribute("labels", label);
		model.addAttribute("capacity", event.getCapacity());
		model.addAttribute("date", event.getDate().toString());
		model.addAttribute("site", "EV/ED");
		this.editedEvent = event;
		return "EventCreatorTemplate";
	}

	@GetMapping("/admin/graph-event")
	public String graphEvent(@RequestParam String id, Model model) {
		model.addAttribute("site", "GRAFICO");
		setHeader(model);
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

		setHeader(model);
		model.addAttribute("image", event.getBannerUrl());
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		model.addAttribute("date", event.getDate());
		model.addAttribute("capacity", event.getCapacity() - event.getlikes());
		model.addAttribute("id", event.getId());
		return getSingleEvent(model);
	}

	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model) {

		Event event = eRepository.findByid(Long.parseLong(id));
		if (this.loggedUser.isLoggedUser() && !this.loggedUser.hasLiked(event.getId())) {
			this.loggedUser.like(event);
			eRepository.save(event);
		} else {
			return getProfile(model);
		}

		return getEvents(model);
	}

	@GetMapping("/admin/graph-tables")
	public String graphTables(Model model) {
		model.addAttribute("Admin", "Administrador");
		model.addAttribute("Logout", "Cerrar sesión");
		model.addAttribute("nombre", "Admin");
		List<TableReservation> reservations = trrepository.findAll();

		Integer numPC = 0;
		Integer numXBOX_ONE = 0;
		Integer numPS5 = 0;

		for (TableReservation tableReservation : reservations) {
			Tablegame table = trepository.findById(tableReservation.getId_table());

			switch (table.getType()) {
			case "PC":
				numPC++;
				break;
			case "XBOX_ONE":
				numXBOX_ONE++;
				break;
			case "PS5":
				numPS5++;
				break;
			default:
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
		setHeader(model);
		if (this.loggedUser.isAdmin()) {
			model.addAttribute("name", "Nombre del evento*");
			model.addAttribute("description", "Descripción del evento");
			model.addAttribute("labels", "SHOOTER/MOBA/MMO/");
			model.addAttribute("capacity", "");
			model.addAttribute("date", "0000-00-00");
			model.addAttribute("site", "EVENTO+");
			return "EventCreatorTemplate";
		}
		return getProfile(model);
	}

	@GetMapping("/user")
	public String getUser(Model model) {
		if (this.loggedUser.isLoggedUser()) {
			setHeader(model);
			List<Event> events=new ArrayList<Event>();
			for(Long ID: this.loggedUser.getLoggedUser().getEventsLiked()){
				events.add(eRepository.findByid(ID));
			}
			model.addAttribute("events", events);
			model.addAttribute("site", "PERFIL");
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
		model.addAttribute("site", "EDITAR PERFIL");
		setHeader(model);

		String name = loggedUser.getLoggedUser().getName();
		String surname = loggedUser.getLoggedUser().getLastName();
		model.addAttribute("Name", name.equals("") ? "Nombre*" : name);
		model.addAttribute("Surname", name.equals("") ? "Apellidos*" : surname);

		return "EditProfileTemplate";
	}

	@GetMapping("/register")
	public String getLoginRegister(Model model) {
		model.addAttribute("site", "INICIAR SESION");
		model.addAttribute("Registered", "");
		setHeader(model);
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
		/*Files.createDirectories(IMAGES_FOLDER);
		Path imagePath = IMAGES_FOLDER.resolve("image" + aux.getEmail() + ".jpg");
		image.transferTo(imagePath);*/
		if (!image.isEmpty()) {
			aux.setProfileSrc(imageService.uploadImage(image));
		} else {
			aux.setProfileSrc("images/uploads/defaultEvent.png");
		}
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
			@RequestParam Integer capacity, @RequestParam String labels, @RequestParam String end, @RequestParam MultipartFile image, HttpSession sesion,
			Model model) {
		Event event;
		if (this.editedEvent != null) {
			event = this.editedEvent;
			this.editedEvent = null;
			for (String var : labels.split("/")) {
				event.putLavel(var.toUpperCase());
				if (!Event.allLabels.contains(var.toUpperCase())) {
					Event.allLabels.add(var.toUpperCase());
				}
			}
			event.setCapacity(capacity!=0 ? capacity : event.getCapacity());
			event.setDescription(description.equals("") ? event.getDescription() : description);
			event.setName(name.equals("") ? event.getName() : name);
			event.setDate(end.equals("") ? event.getDate().toString() : end);
		} else {
			//event = new Event(name, description, end, "", capacity);
			event = eventService.createNewEvent(name, description, image, end, capacity);
			for (String var : labels.split("/")) {
				event.putLavel(var.toUpperCase());
				if (!Event.allLabels.contains(var.toUpperCase())) {
					Event.allLabels.add(var.toUpperCase());
				}
			}

		}
		eRepository.save(event);
		return getAdmin(model);
	}

}
