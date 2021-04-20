package urjc.ugc.ultragamecenter.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;
import urjc.ugc.ultragamecenter.services.*;

@Controller
public class LoginController {

	@Autowired
	UserService uService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("isAdmin", uDetails.isLoggedUserADMIN());
		model.addAttribute("isLoged", uDetails.idLoggedUser());
	}

	@GetMapping("/login")
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

	@GetMapping("/register")
	public String getLoginRegister(Model model) {

		setHeader(model);
		model.addAttribute("site", "REGISTRATE");
		model.addAttribute("Registered", "");
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password,
			@RequestParam String email, Model model) {
		User u = uService.createNewUser(name, lastName, password, email);
		if (u == null) {
			setHeader(model);
			model.addAttribute("site", "REGISTRATE");
			model.addAttribute("Registered", "El email ya existe");
			return "register";
		}
		return "redirect:/";
	}

}
