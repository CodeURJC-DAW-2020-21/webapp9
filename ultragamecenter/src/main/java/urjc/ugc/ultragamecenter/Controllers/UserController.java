package urjc.ugc.ultragamecenter.controllers;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.repositories.*;
import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.components.*;

import org.springframework.context.ApplicationContext;

@Controller
public class UserController {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private UserRepository urepository;

	@Autowired
	private UserService uservice;

	@Autowired
	UserComponent userComponent;

	@Autowired
	EventRepository eRepository;

	@Autowired
	TableRepository trepository;

	@Autowired
	TableReservationRepository trrepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private EventService eventService;

	@Autowired
	private ImageService imageService;

	private Event editedEvent = null;

	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";// -----------------tableController--------------------
	private final static Logger LOG = Logger.getLogger("urjc.ugc.ultragamecenter.controllers.TableController");
	private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

	@GetMapping("/")
	public String getIndex(Model model) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "index";
	}

	@GetMapping("/reservation")
	public String getReservation(Model model) {

		model.addAttribute("site", "MESAS");
		model.addAttribute("full", "");
		setHeader(model);
		model.addAttribute("logged", !this.userComponent.isLoggedUser());
		return "ReservationTemplate";
	}

	@GetMapping("/single-event")
	public String getSingleEvent(Model model) {
		model.addAttribute("site", "EVENTO");
		setHeader(model);
		return "SingleEventTemplate";

	}

	@GetMapping("/events")
	public String getEvents(Model model, @RequestParam(required = false, defaultValue = "3") int pageSize) {
		setHeader(model);
		model.addAttribute("site", "EVENTOS");
		Page<Event> events = eventService.getPageEvents(0, pageSize);
		model.addAttribute("events", events);
		model.addAttribute("isLogged", this.userComponent.isLoggedUser());
		return "events";
	}

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
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fa fa-star" : "");
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
		model.addAttribute("gallery", event.getGallery());
		return getSingleEvent(model);
	}

	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model) {
		Event event = eRepository.findByid(Long.parseLong(id));
		if (this.userComponent.isLoggedUser() && !this.userComponent.hasLiked(event.getId())) {
			this.userComponent.like(event, this.eRepository.findAll());
			eRepository.save(event);
			urepository.save(this.userComponent.getLoggedUser());
		} else {
			return getProfile(model);
		}

		return getEvents(model, 3);
	}

	@GetMapping("/admin/graph-tables")
	public String graphTables(Model model) {
		model.addAttribute("nombre", "Admin");
		model.addAttribute("site", "GRAFICO");
		setHeader(model);
		List<TableReservation> reservations = trrepository.findAll();

		Integer numPC = 0;
		Integer numXBOX_ONE = 0;
		Integer numPS5 = 0;

		for (TableReservation tableReservation : reservations) {
			Optional<Tablegame> opttable = trepository.findById(tableReservation.getId_table());
			if (opttable.isPresent()) {
				Tablegame table = opttable.get();
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
		}
		model.addAttribute("numPC", numPC);
		model.addAttribute("numXBOX_ONE", numXBOX_ONE);
		model.addAttribute("numPS5", numPS5);
		return "GraphsTableTemplate";
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
		return getAdmin(model);
	}

	@GetMapping("/Event-Adder")
	public String getEventAdder(Model model) {
		setHeader(model);
		if (this.userComponent.isAdmin()) {
			model.addAttribute("name", "Nombre del evento*");
			model.addAttribute("description", "Descripción del evento");
			model.addAttribute("labels", "SHOOTER/MOBA/MMO/PRESENTATION");
			model.addAttribute("capacity", "");
			model.addAttribute("date", "0000-00-00");
			model.addAttribute("site", "EVENTO+");
			return "EventCreatorTemplate";
		}
		return getProfile(model);
	}

	@GetMapping(value = "/login")
	public String getLogin(Model model) {
		setHeader(model);
		model.addAttribute("site", "LOGIN");
		model.addAttribute("loginerror", false);
		model.addAttribute("Registered", "");
		return "login";
	}

	@GetMapping("/loginerror")
	public String loginerror(Model model) {
		model.addAttribute("loginerror", true);
		return "login";
	}

	@GetMapping("/profile")
	public String getProfile(Model model) {

		setHeader(model);
		boolean isLogged = userComponent.isLoggedUser();
		User userLogged = userComponent.getLoggedUser();

		if (!isLogged) {
			return getLogin(model);
		}
		ArrayList<Event> aux = new ArrayList<Event>();
		for (Long l : this.userComponent.getLoggedUser().getEventsLiked()) {
			aux.add(eRepository.findByid(l));
		}
		setHeader(model);
		model.addAttribute("site", "PERFIL");
		model.addAttribute("name", userLogged.getName());
		model.addAttribute("lastname", userLogged.getLastName());
		model.addAttribute("email", userLogged.getEmail());
		model.addAttribute("profileSrc", userLogged.getProfileSrc());
		model.addAttribute("events_r", this.userComponent.sort(eRepository.findAll(), 3));
		model.addAttribute("tables", this.userComponent.getLoggedUser().getReferencedCodes());
		model.addAttribute("events", aux);

		return "user";
	}

	@GetMapping("/get-user-image")
	public ResponseEntity<Object> downloadImage(Model model) throws MalformedURLException {
		User aux = this.userComponent.getLoggedUser();
		Path imagePath = IMAGES_FOLDER.resolve("image" + aux.getEmail() + ".jpg");

		Resource image = new UrlResource(imagePath.toUri());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);
	}

	@GetMapping("/edit-profile")
	public String editProfile(Model model) {
		model.addAttribute("site", "EDITAR PERFIL");
		setHeader(model);
		String name = userComponent.getLoggedUser().getName();
		String surname = userComponent.getLoggedUser().getLastName();
		model.addAttribute("Name", name.equals("") ? "Nombre*" : name);
		model.addAttribute("Surname", name.equals("") ? "Apellidos*" : surname);

		return "EditProfileTemplate";
	}

	@GetMapping("/register")
	public String getLoginRegister(Model model) {
		model.addAttribute("site", "REGISTRATE");
		model.addAttribute("Registered", "");
		setHeader(model);
		return "register";
	}

	@GetMapping("/loggout")
	public String LoggOut(HttpSession sesion, Model model) {
		this.userComponent.logOut();
		return getProfile(model);
	}

	@PostMapping("/logginUser")
	public String logearUsuario(@RequestParam String email, @RequestParam String password, HttpSession sesion,
			Model model) {
		User aux = urepository.findByEmail(email);
		if (aux != null) {
			if (aux.matchPasword(password)) {
				this.userComponent.setLoggedUser(aux);
				return getProfile(model);
			} else {
				model.addAttribute("Registered", "La contraseña va a ser que no es");
			}
		} else {
			model.addAttribute("Registered", "Esa cuenta no existe");
		}
		model.addAttribute("site", "INICIAR SESION");
		setHeader(model);
		return "login";
	}

	@PostMapping("/editPassword")
	public String editPassword(@RequestParam String password, @RequestParam String password_repeated,
			@RequestParam String new_password, HttpSession sesion) {
		User aux = this.userComponent.getLoggedUser();
		if (aux.getPassword().equals(password) && password.equals(password_repeated)) {
			aux.setPassword(new_password);
		}
		urepository.save(aux);
		return "EditProfileTemplate";
	}

	@PostMapping("/editProfile")
	public String editProfile(@RequestParam String name, @RequestParam String surname,
			@RequestParam MultipartFile image, HttpSession sesion) throws IOException {
		User aux = this.userComponent.getLoggedUser();
		if (!image.isEmpty()) {
			aux.setProfileSrc(imageService.uploadImage(image));
		} else {
			aux.setProfileSrc("images/uploads/defaultuser.png");
		}
		if (!surname.equals("")) {
			aux.setLastName(surname);
		}
		if (!name.equals("")) {
			aux.setName(name);
		}
		urepository.save(aux);
		return "redirect:/";
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
			getLogin(model);
		}
		return getLoginRegister(model);
	}

	@PostMapping("/createEvent")
	public String registrarUsuario(@RequestParam String name, @RequestParam String description,
			@RequestParam Integer capacity, @RequestParam String labels, @RequestParam String end,
			@RequestParam MultipartFile image, HttpSession sesion, Model model, @RequestParam MultipartFile image1,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3) {
		Event event;
		if (this.editedEvent != null) {
			event = this.editedEvent;
			this.editedEvent = null;
			for (String var : labels.split("/")) {
				event.putLavel(var.toUpperCase());
			}
			event.setCapacity(capacity != 0 ? capacity : event.getCapacity());
			event.setDescription(description.equals("") ? event.getDescription() : description);
			event.setName(name.equals("") ? event.getName() : name);
			event.setDate(end.equals("") ? event.getDate().toString() : end);
		} else {
			event = eventService.createNewEvent(name, description, image, image1, image2, image3, end, capacity);
			for (String var : labels.split("/")) {
				event.putLavel(var.toUpperCase());
			}

		}
		eRepository.save(event);
		return getAdmin(model);
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("site", "ADMIN");
		setHeader(model);
		if (this.userComponent.isAdmin()) {
			model.addAttribute("name", "Admin");
			model.addAttribute("events", eRepository.findAll());
			return "admin";
		}
		return getProfile(model);

	}

	@PostMapping("/trytoreserve")
	public String reserve(@RequestParam String type, @RequestParam String day, @RequestParam String hour,
			@RequestParam(required = false) String email, Model model) {
		Long table_id = 0L;
		Integer hour_int = Integer.parseInt(hour);
		java.sql.Date sqldate = java.sql.Date.valueOf(day);
		List<Tablegame> tables = trepository.findByTypeAndDate(type, sqldate);
		boolean reserved = false;
		int i = 0;
		while (!reserved && (i != tables.size())) {
			if (tables.get(i).getState().get(hour_int) == 0) {
				tables.get(i).setState(hour_int, 1);
				table_id = tables.get(i).getId();
				trepository.saveAll(tables);
				reserved = true;
			}
			i++;
		}
		if (!reserved) {// not reserved
			String full = "No hay disponibilidad de mesas de " + type + " para el dia " + day
					+ " en la hora seleccionada";
			model.addAttribute("full", full);
			setHeader(model);
			model.addAttribute("site", "MESAS");
			return "ReservationTemplate";
		} else {// reserved

			if (this.userComponent.isLoggedUser()) {// logged user
				Integer id = this.userComponent.getLoggedUser().getId();
				Optional<User> optUser = urepository.findById(id);
				User logUser = optUser.get();
				String randomCode = randomRefCode();
				logUser.addReferencedCode(randomCode);
				urepository.save(logUser);
				TableReservation tReserve = new TableReservation(table_id, randomCode, hour_int);
				trrepository.save(tReserve);
			} else { // guest user
				if (!email.equals("")) {
					String randomCode = randomRefCode();
					TableReservation tReserve = new TableReservation(table_id, randomCode, hour_int);
					trrepository.save(tReserve);
					try {
						this.sendMail(email, randomCode);
					} catch (MessagingException exc) {
						
					}
				} else {
					// no pasan email
				}
			}
		}

		return getReservation(model);
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password,
			@RequestParam String email) {
		User user = uservice.createNewUser(name, lastName, password, email);
		userComponent.setLoggedUser(user);
		this.urepository.save(user);
		return "redirect:/";
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
}
