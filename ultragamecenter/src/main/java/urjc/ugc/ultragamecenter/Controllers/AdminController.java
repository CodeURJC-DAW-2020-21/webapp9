package urjc.ugc.ultragamecenter.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import urjc.ugc.ultragamecenter.services.EventService;
import urjc.ugc.ultragamecenter.services.TableReservationService;
import urjc.ugc.ultragamecenter.services.TableService;
import urjc.ugc.ultragamecenter.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService uService;

	@Autowired
	EventService eService;

	@Autowired
	TableService tService;

	@Autowired
	TableReservationService trService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl uDetails;

	public void setHeader(Model model) {
		model.addAttribute("isAdmin", uDetails.isLoggedUserADMIN());
		model.addAttribute("isLoged",  uDetails.idLoggedUser());
	}

	@GetMapping("/event-edit")
	public String editEvent(@RequestParam String id, Model model) {
		setHeader(model);
		Event event = eService.getByid(Long.parseLong(id));
		model.addAttribute("id", id);
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		StringBuilder label = new StringBuilder();
		for (String x : event.getLavels()) {
			label.append(x);
			label.append("/");
		}
		model.addAttribute("labels", label.toString());
		model.addAttribute("capacity", event.getCapacity());
		model.addAttribute("date", event.getDate().toString());
		model.addAttribute("site", "EV/ED");
		return "EditEventTemplate";

	}

	@GetMapping("/graph-event")
	public String graphEvent(@RequestParam String id, Model model) {
		setHeader(model);
		model.addAttribute("site", "GRAFICO");
		Event event = eService.getByid(Long.parseLong(id));
		Integer likes = event.getlikes();
		model.addAttribute("likes", likes);
		Integer plazasLibres = event.getCapacity() - likes;
		model.addAttribute("plazasLibres", plazasLibres);
		return "GraphsEventsTemplate";
	}

	@GetMapping("/delete-event")
	public String borrarEvento(@RequestParam String id, Model model) {
		setHeader(model);

		eService.deleteID(Long.parseLong(id));
		model.addAttribute("events", eService.getAllEvents());

		return getAdmin(model);
	}

	@GetMapping("/add-event")
	public String getEventAdder(Model model) {
		setHeader(model);
		model.addAttribute("name", "Nombre del evento*");
		model.addAttribute("description", "Descripción del evento");
		model.addAttribute("labels", "SHOOTER/MOBA/MMO/PRESENTATION");
		model.addAttribute("capacity", "");
		model.addAttribute("date", "2021-01-24");
		model.addAttribute("site", "EVENTO+");
		return "EventCreatorTemplate";
	}

	@GetMapping("")
	public String getAdmin(Model model) {
		setHeader(model);
		model.addAttribute("site", "ADMIN");
		model.addAttribute("name", "Admin");
		model.addAttribute("events", eService.getAllEvents());
		return "admin";
	}

	@PostMapping("/createEvent")
	public String registrarUsuario(@RequestParam String name, @RequestParam String description,
			@RequestParam(defaultValue = "") Integer capacity, @RequestParam String labels, @RequestParam String end,
			@RequestParam MultipartFile image, HttpSession sesion, Model model, @RequestParam MultipartFile image1,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3, HttpServletRequest request) {
		MultipartFile[] filePack = { image1, image2, image3 };
		eService.createNewEvent(name, description, image, filePack, end, capacity, labels);
		return "redirect:/admin";
	}

	@PostMapping("/editEvent")
	public String editEvent(@RequestParam String id, @RequestParam String name, @RequestParam String description,
			@RequestParam Integer capacity, @RequestParam String labels, @RequestParam String end,
			@RequestParam MultipartFile image, HttpSession sesion, Model model, @RequestParam MultipartFile image1,
			@RequestParam MultipartFile image2, @RequestParam MultipartFile image3, HttpServletRequest request) {
		MultipartFile[] filePack = { image1, image2, image3 };
		eService.updateEvent(Long.parseLong(id), name, description, end, capacity, image, filePack);
		return "redirect:/admin";
	}

}
