package urjc.ugc.ultragamecenter.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.requests.LoginRequest;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;
import urjc.ugc.ultragamecenter.services.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Controller
public class LoginController {

	@Autowired
	UserService uService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("Admin", uDetails.idLoggedUser() ? "Admin" : "");
		model.addAttribute("Logout",  uDetails.isLoggedUserADMIN() ? "Log Out" : "" );
		model.addAttribute("Admin-ico", uDetails.isLoggedUserADMIN() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", uDetails.idLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

	@GetMapping("/login")
	public String getLogin(Model model, HttpServletRequest request) {

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
	public String getLoginRegister(Model model, HttpServletRequest request) {

		setHeader(model);
		model.addAttribute("site", "REGISTRATE");
		model.addAttribute("Registered", "");
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password,
			@RequestParam String email) {
		uService.createNewUser(name, lastName, password, email);
		return "redirect:/";
	}

}
