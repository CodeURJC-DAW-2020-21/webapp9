package urjc.ugc.ultragamecenter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.*;

@Controller
public class LoginController {
	
	@GetMapping("/")
	public String getIndex(Model model) {
		//setHeader(model);
		//model.addAttribute("site", "INICIO");
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	
	@GetMapping("/loginerror")
	public String loginerror(Model model) {
		model.addAttribute("loginerror", true);
		return "login";
	}

	
	@GetMapping(value = "/register")
	public String register() {
		return "register";
	}
}
