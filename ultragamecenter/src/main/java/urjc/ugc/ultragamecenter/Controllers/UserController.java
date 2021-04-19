package urjc.ugc.ultragamecenter.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;
import urjc.ugc.ultragamecenter.services.*;

@Controller
public class UserController {

	@Autowired
	UserService uService;

	@Autowired
	EventService eService;

	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";

	@Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("isAdmin", uDetails.isLoggedUserADMIN() );
		model.addAttribute("isLoged",  uDetails.idLoggedUser());
	}

	@GetMapping("/")
	public String getIndex(Model model, HttpServletRequest request) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "index";
	}

	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model, HttpServletRequest request) {
		if (!eService.like(Long.parseLong(id))) {
			return "redirect:/profile";
		}
		return "redirect:/events";
	}

	@GetMapping("/profile")
	public String getProfile(Model model, HttpServletRequest request) {

		setHeader(model);
		User userLogged = uService.findByEmail(uDetails.getEmail());
		ArrayList<Event> aux = new ArrayList<>();
		for (Long l : userLogged.getEventsLiked()) {
			aux.add(eService.getByid(l));
		}
		model.addAttribute("site", "PERFIL");
		model.addAttribute("name", userLogged.getName());
		model.addAttribute("lastname", userLogged.getLastName());
		model.addAttribute("email", userLogged.getEmail());
		model.addAttribute("profileSrc", userLogged.getProfileSrc());
		model.addAttribute("events_r", uService.getRecomendatedEvents(3));
		model.addAttribute("tables", userLogged.getReferencedCodes());
		model.addAttribute("events", aux);
		return "user";
	}

	@GetMapping("/edit-profile")
	public String editProfile(Model model) {
		User userLogged = uService.findByEmail(uDetails.getEmail());
		model.addAttribute("site", "EDITAR PERFIL");
		setHeader(model);
		String name = userLogged.getName();
		String surname = userLogged.getLastName();
		model.addAttribute("Name", name.equals("") ? "Nombre*" : name);
		model.addAttribute("Surname", name.equals("") ? "Apellidos*" : surname);

		return "EditProfileTemplate";
	}

	@PostMapping("/editPassword")
	public String editPassword(@RequestParam String password, @RequestParam String newPassword, HttpSession sesion) {
		uService.updateUserPassword(password, newPassword);
		return "redirect:/";
	}

	@PostMapping("/editProfile")
	public String editProfile(@RequestParam String name, @RequestParam String surname,
			@RequestParam MultipartFile image, HttpSession sesion) {
		uService.updateUser(name, surname, image);
		return "redirect:/";
	}

}
