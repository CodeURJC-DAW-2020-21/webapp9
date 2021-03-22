package urjc.ugc.ultragamecenter.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.components.*;
import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.repositories.*;
import urjc.ugc.ultragamecenter.services.*;

@Controller
public class AdminController {
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	UserComponent userComponent;

	@Autowired
	EventRepository eRepository;

	@Autowired
	TableRepository trepository;

	@Autowired
	TableReservationRepository trrepository;
	
	@Autowired
     AuthenticationManager authenticationManager;
	
	private Event editedEvent = null;

	
	public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

	@GetMapping("/add-event")
	public String getEventAdder(Model model) {
		setHeader(model);
		if (this.userComponent.isAdmin()) {
			model.addAttribute("name", "Nombre del evento*");
			model.addAttribute("description", "Descripción del evento");
			model.addAttribute("labels", "SHOOTER/MOBA/MMO/");
			model.addAttribute("capacity", "");
			model.addAttribute("date", "0000-00-00");
			model.addAttribute("site", "EVENTO+");
			return "EventCreatorTemplate";
		}
		return "404";
	}

	
	
	
	
	
	
	/*
	@GetMapping("/admin/event-edit")
	public String editEvent(@RequestParam String id, Model model) {
		//setHeader(model);
		Event event = eRepository.findByid(Long.parseLong(id));
		model.addAttribute("name", event.getName());
		model.addAttribute("description", event.getDescription());
		String label = "";
		for (String x : event.getLavels()) {
			label += x;
			label += "/";
		}
		model.addAttribute("labels", label);
		model.addAttribute("capacity", event.getCapacity());
		model.addAttribute("date", event.getDate().toString());
		model.addAttribute("site", "EV/ED");
		//this.editedEvent = event;
		return "EventCreatorTemplate";
	}

	@GetMapping("/admin/graph-event")
	public String graphEvent(@RequestParam String id, Model model) {
		model.addAttribute("site", "GRAFICO");
		//setHeader(model);
		Event event = eRepository.findByid(Long.parseLong(id));
		Integer likes = event.getlikes();
		model.addAttribute("likes", likes);
		Integer plazasLibres = event.getCapacity() - likes;
		model.addAttribute("plazasLibres", plazasLibres);
		return "GraphsEventsTemplate";
	}
	
	@GetMapping("/admin/delete-reservation")
	public String borrarReserva(@RequestParam String id, Model model) {
		TableReservation reserva = trrepository.findByid(Long.parseLong(id));
		trrepository.delete(reserva);

		model.addAttribute("events", eRepository.findAll());
		model.addAttribute("reservations", trrepository.findAll());
		return getAdmin(model);
	}

	@GetMapping("/admin/delete-event")
	public String borrarEvento(@RequestParam String id, Model model) {
		Event evento = eRepository.findByid(Long.parseLong(id));
		if (evento != null) {
			eRepository.delete(evento);
		}
		model.addAttribute("events", eRepository.findAll());
		model.addAttribute("reservations", trrepository.findAll());
		return getAdmin(model);
	}
	
	@GetMapping("/admin/graph-tables")
	public String graphTables(Model model) {
		model.addAttribute("Admin", "Administrador");
		model.addAttribute("Logout", "Cerrar sesión");
		model.addAttribute("nombre", "Admin");
		List<TableReservation> reservations = trrepository.findAll();

		Integer numPC = 0;
		Integer numXBOX_ONE = 0;
		Integer numPS5 = 0;

		for (TableReservation tableReservation : reservations) {
			Tablegame table = trepository.findById(tableReservation.getId_table());

			switch (table.getType()) {
			case "PC":
				numPC++;
				break;
			case "XBOX_ONE":
				numXBOX_ONE++;
				break;
			case "PS5":
				numPS5++;
				break;
			default:
			}
		}
		model.addAttribute("numPC", numPC);
		model.addAttribute("numXBOX_ONE", numXBOX_ONE);
		model.addAttribute("numPS5", numPS5);
		return "GrapsTablesTemplate";
	}
	
	*/

}
