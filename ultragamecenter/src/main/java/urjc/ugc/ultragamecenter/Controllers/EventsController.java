package urjc.ugc.ultragamecenter.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.*;
import urjc.ugc.ultragamecenter.components.*;

@Controller

public class EventsController {

	@Autowired
	UserComponent userComponent;

	@Autowired
	EventService eService;

	@Autowired
	AuthenticationManager authenticationManager;

	public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

	@GetMapping("/single-event")
	public String getSingleEvent(Model model) {
		model.addAttribute("site", "EVENTO");
		setHeader(model);
		return "SingleEventTemplate";

	}

	@GetMapping("/events")
	public String getEvents(Model model, @RequestParam(required = false, defaultValue = "3") int pageSize) {
		setHeader(model);
		model.addAttribute("site", "EVENTOS");
		Page<Event> events = eService.getPageEvents(0, pageSize);
		model.addAttribute("events", events);
		model.addAttribute("isLogged", this.userComponent.isLoggedUser());
		return "events";
	}

	@GetMapping("/events/see-event")
	public String seeEvent(@RequestParam String id, Model model) {
		Event event = eService.getByid(Long.parseLong(id));

		setHeader(model);
		model.addAttribute("image", event.getBannerUrl());
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		model.addAttribute("date", event.getDate());
		model.addAttribute("capacity", event.getCapacity() - event.getlikes());
		model.addAttribute("id", event.getId());
		model.addAttribute("gallery", event.getGallery());
		return getSingleEvent(model);
	}

	@PostMapping("/createEvent")
	public String registrarUsuario(@RequestParam String name, @RequestParam String description,
			@RequestParam(defaultValue = "") Integer capacity, @RequestParam String labels, @RequestParam String end,
			@RequestParam MultipartFile image, HttpSession sesion, Model model, @RequestParam MultipartFile image1,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3) {
		MultipartFile[] filePack = { image1, image2, image3 };
		eService.createNewEvent(name, description, image, filePack, end, capacity, labels);
		return "redirect:/admin";
	}

	@PostMapping("/editEvent")
	public String editEvent(@RequestParam String id, @RequestParam String name, @RequestParam String description,
			@RequestParam Integer capacity, @RequestParam String labels, @RequestParam String end,
			@RequestParam MultipartFile image, HttpSession sesion, Model model, @RequestParam MultipartFile image1,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3) {
			System.out.println("\n\n\n\n");
			System.out.println(name);
			System.out.println(description);
			System.out.println(capacity);
			System.out.println(labels);
			System.out.println(end);
			System.out.println(image.isEmpty());
			System.out.println(image1.isEmpty());
			System.out.println(image2.isEmpty());
			System.out.println(image3.isEmpty());
			System.out.println("\n\n\n\n");
		MultipartFile[] filePack = { image1, image2, image3 };
		eService.updateEvent(Long.parseLong(id), name, description, end, capacity, image, filePack);
		return "redirect:/admin";
	}

}
