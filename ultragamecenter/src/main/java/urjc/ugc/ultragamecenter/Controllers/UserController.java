package urjc.ugc.ultragamecenter.Controllers;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Services.*;
import urjc.ugc.ultragamecenter.Components.*;


@Controller
public class UserController {

	@Autowired
	UserService uService;

	@Autowired
	UserComponent userComponent;

	@Autowired
	EventService eService;


	public static final String IMG_FOLDER = "src/main/resources/static/images/uploads/";
	public static final String IMG_CONTROLLER_URL = "/images/uploads/";

	@GetMapping("/")
	public String getIndex(Model model) {
		setHeader(model);
		model.addAttribute("site", "INICIO");
		return "index";
	}




	@GetMapping("/like")
	public String likeEvent(@RequestParam String id, Model model) {
		if(!eService.like(Long.parseLong(id))) {
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
		ArrayList<Event> aux = new ArrayList<>();
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
	public String editPassword(@RequestParam String password,
			@RequestParam String newPassword, HttpSession sesion) {
		uService.updateUserPassword(password, newPassword);
		return "EditProfileTemplate";
	}

	@PostMapping("/editProfile")
	public String editProfile(@RequestParam String name, @RequestParam String surname,
			@RequestParam MultipartFile image, HttpSession sesion) {
		uService.updateUser(name, surname,image);
		return "redirect:/";
	}

	public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
	}


}
