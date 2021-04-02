package urjc.ugc.ultragamecenter.controllers;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.repositories.*;
import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.components.*;


@Controller
public class UserController {

	@Autowired
	UserService uService;

	@Autowired
	UserComponent userComponent;

	@Autowired
	EventService eService;

	@Autowired
	TableService tService;

	@Autowired
	TableReservationService trrService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private ImageService imageService;


	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";// -----------------tableController--------------------

	@GetMapping("/")
	public String getIndex(Model model) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "index";
	}

	


	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model) {
		Event event = eService.getByid(Long.parseLong(id));
		if (this.userComponent.isLoggedUser() && !this.userComponent.hasLiked(event.getId())) {
			this.userComponent.like(event, this.eService.getAllEvents());
			eService.save(event);
			uService.save(this.userComponent.getLoggedUser());
		} else {
			return getProfile(model);
		}

		return "redirect:/events";
	}

	@GetMapping("/profile")
	public String getProfile(Model model) {

		setHeader(model);
		boolean isLogged = userComponent.isLoggedUser();
		User userLogged = userComponent.getLoggedUser();

		if (!isLogged) {
			return "redirect:/login";
		}
		ArrayList<Event> aux = new ArrayList<Event>();
		for (Long l : this.userComponent.getLoggedUser().getEventsLiked()) {
			aux.add(eService.getByid(l));
		}
		setHeader(model);
		model.addAttribute("site", "PERFIL");
		model.addAttribute("name", userLogged.getName());
		model.addAttribute("lastname", userLogged.getLastName());
		model.addAttribute("email", userLogged.getEmail());
		model.addAttribute("profileSrc", userLogged.getProfileSrc());
		model.addAttribute("events_r", this.userComponent.sort(eService.getAllEvents(), 3));
		model.addAttribute("tables", this.userComponent.getLoggedUser().getReferencedCodes());
		model.addAttribute("events", aux);

		return "user";
	}

	@GetMapping("/edit-profile")
	public String editProfile(Model model) {
		if(this.userComponent.isLoggedUser()){
			model.addAttribute("site", "EDITAR PERFIL");
			setHeader(model);
			String name = userComponent.getLoggedUser().getName();
			String surname = userComponent.getLoggedUser().getLastName();
			model.addAttribute("Name", name.equals("") ? "Nombre*" : name);
			model.addAttribute("Surname", name.equals("") ? "Apellidos*" : surname);
	
			return "EditProfileTemplate";
		}
		return "redirect:/";
	}


	@PostMapping("/editPassword")
	public String editPassword(@RequestParam String password, @RequestParam String password_repeated,
			@RequestParam String new_password, HttpSession sesion) {
		User aux = this.userComponent.getLoggedUser();
		if (aux.getPassword().equals(password) && password.equals(password_repeated)) {
			aux.setPassword(new_password);
		}
		uService.save(aux);
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
		uService.save(aux);
		return "redirect:/";
	}

	public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

	
}
