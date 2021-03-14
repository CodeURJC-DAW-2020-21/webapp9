package urjc.ugc.ultragamecenter.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/register")
	public String getLoginRegister(Model model) {
		model.addAttribute("nombre4", "Register Page");
		return "LoginRegisterTemplate";
	}

	@PostMapping("/registerUser")
	public String registrarUsuario(@RequestParam String name, @RequestParam String email, @RequestParam String password, HttpSession sesion) {
		User aux = userRepo.findByEmail(email);
		if (aux != null) {
			// significa que existe ya un usuario con ese email
		} else {
			User user = new User(name, password, "", email, "", "Registered");
			userRepo.save(user);
		}
		return "LoginRegisterTemplate";
	}

	@GetMapping("/login")
	public String login(Model model, HttpSession sesion) {

		return "register";
	}
	

}
