package urjc.ugc.ultragamecenter.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.components.*;
import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.repositories.*;
import urjc.ugc.ultragamecenter.services.*;

@Controller
public class UserController {
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

	private Event editedEvent = null;

	private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
	
	
	public void setHeader(Model model) {

	}
	
	@GetMapping("/")
	public String getIndex(Model model) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "index";
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String name,@RequestParam String lastName,@RequestParam String password,@RequestParam String email) {
		User user = uservice.createNewUser(name,lastName,password,email);
		userComponent.setLoggedUser(user);
		return "redirect:/" ;
	}
	
	@GetMapping("/reservation")
	public String getReservation(Model model) {
		model.addAttribute("site", "MESAS");
		setHeader(model);
		return "ReservationTemplate";
	}

	@GetMapping("/single-event")
	public String getSingleEvent(Model model) {
		model.addAttribute("site", "EVENTO");
		setHeader(model);
		return "SingleEventTemplate";

	}

	@GetMapping("/events")
	public String getEvents(Model model) {
		setHeader(model);
		model.addAttribute("site", "EVENTOS");
		model.addAttribute("events", this.userComponent.sort(eRepository.findAll()));
		model.addAttribute("isLogged", this.userComponent.isLoggedUser());
		return "events";
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
	
	public String likeEvent(@RequestParam(value="Asistir") String id, Model model) {

		Event event = eRepository.findByid(Long.parseLong(id));
		//this.userComponent.like(event);
		event.like();
		eRepository.save(event);
		return "/events";
	}
	
	/*
	@GetMapping("/user")
	public String getUser(Model model) {
		if (this.loggedUser.isLoggedUser()) {
			setHeader(model);
			List<Event> events=new ArrayList<Event>();
			for(Long ID: this.loggedUser.getLoggedUser().getEvents()){
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
	
	*/

	@GetMapping("/profile")
	public String getProfile(Model model) {
		
		//setHeader(model);
		boolean isLogged = userComponent.isLoggedUser();
		User userLogged = userComponent.getLoggedUser();
		
		if(!isLogged){
			return "redirect:/login";
		}
		
		model.addAttribute("admin", userLogged.getRoles().contains("ADMIN"));
		model.addAttribute("isLogged", isLogged);
		model.addAttribute("name", userLogged.getName());
		model.addAttribute("lastname", userLogged.getLastName());
		model.addAttribute("email", userLogged.getEmail());
		
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
}
