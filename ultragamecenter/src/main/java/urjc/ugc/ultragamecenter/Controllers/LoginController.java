package urjc.ugc.ultragamecenter.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.components.*;

@Controller
public class LoginController {

	@Autowired
	UserService uService;

	@Autowired
	UserComponent userComponent;

	@Autowired
	AuthenticationManager authenticationManager;


    public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
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
		return "redirect:/profile";
	}

	public void logUsr(String email, String password){	
		User aux = uService.findByEmail(email);
		if (aux != null && aux.matchPasword(password)) {
			this.userComponent.setLoggedUser(aux);
		}
	}

	@PostMapping("/logginUser")
	public String logUser(@RequestParam String email, @RequestParam String password, HttpSession sesion,
			Model model) {
		User aux = uService.findByEmail(email);
		if (aux != null) {
			if (aux.matchPasword(password)) {
				this.userComponent.setLoggedUser(aux);
				return "redirect:/profile";
			} else {
				model.addAttribute("Registered", "Las constraseñas no existen");
			}
		} else {
			model.addAttribute("Registered", "Esa cuenta no existe");
		}
		model.addAttribute("site", "INICIAR SESION");
		setHeader(model);
		return "login";
	}

    
	@PostMapping("/registerUser")
	public String regUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String email,
			@RequestParam String password, HttpSession sesion, Model model) {
		User aux = uService.findByEmail(email);
		if (aux != null) {
			model.addAttribute("Registered", "Ya hay un usuario registrado con ese correo");
		} else {
			User user = new User(name, lastName, password, email);
			uService.save(user);
			getLogin(model);
		}
		return getLoginRegister(model);
	}

    @PostMapping("/register")
	public String registerUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password,
			@RequestParam String email) {
		User user = uService.createNewUser(name, lastName, password, email);
		userComponent.setLoggedUser(user);
		this.uService.save(user);
		return "redirect:/";
	}
}
