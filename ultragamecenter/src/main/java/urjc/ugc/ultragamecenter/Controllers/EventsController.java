package urjc.ugc.ultragamecenter.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
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
@RequestMapping("/events")
public class EventsController {

	@Autowired
	EventService eService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService uService;

	@Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("isAdmin", uDetails.isLoggedUserADMIN() );
		model.addAttribute("isLoged",  uDetails.idLoggedUser());
	}

	@GetMapping("")
	public String getEvents(Model model, @RequestParam(required = false, defaultValue = "3") int pageSize, HttpServletRequest request) {
		model.addAttribute("isLogged", uDetails.idLoggedUser());
		setHeader(model);
		model.addAttribute("site", "EVENTOS");
		Page<Event> events = eService.getPageEvents(0, pageSize);
		model.addAttribute("events", events);
		
		return "events";
	}

	@GetMapping("/{id}")
	public String seeEvent(@RequestParam String id, Model model, HttpServletRequest request) {
		Event event = eService.getByid(Long.parseLong(id));
		setHeader(model);
		model.addAttribute("image", event.getBannerUrl());
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		model.addAttribute("date", event.getDate());
		model.addAttribute("capacity", event.getCapacity() - event.getlikes());
		model.addAttribute("id", event.getId());
		model.addAttribute("gallery", event.getGallery());
		model.addAttribute("site", "EVENTO");
		return "SingleEventTemplate";
	}
}
