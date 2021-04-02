package urjc.ugc.ultragamecenter.controllers;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.EventService;
import urjc.ugc.ultragamecenter.services.TableReservationService;
import urjc.ugc.ultragamecenter.services.TableService;
import urjc.ugc.ultragamecenter.components.*;


@Controller
public class AdminController {

	@Autowired
	UserComponent userComponent;

	@Autowired
	EventService eService;

	@Autowired
	TableService tService;

	@Autowired
	TableReservationService trService;

	@Autowired
	AuthenticationManager authenticationManager;

	public void setHeader(Model model) {
		model.addAttribute("Admin", this.userComponent.isAdmin() ? "Admin" : "");
		model.addAttribute("Logout", this.userComponent.isLoggedUser() ? "Log Out" : "");
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fas fa-tools" : "");
		model.addAttribute("Logout-ico", this.userComponent.isLoggedUser() ? "fas fa-sign-out-alt" : "");
	}

	
	@GetMapping("/admin/event-edit")
	public String editEvent(@RequestParam String id, Model model) {
		setHeader(model);
		Event event = eService.getByid(Long.parseLong(id));
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
		return "EventCreatorTemplate";
	}

	@GetMapping("/admin/graph-event")
	public String graphEvent(@RequestParam String id, Model model) {
		model.addAttribute("Admin-ico", this.userComponent.isAdmin() ? "fa fa-star" : "");
		model.addAttribute("site", "GRAFICO");
		setHeader(model);
		Event event = eService.getByid(Long.parseLong(id));
		Integer likes = event.getlikes();
		model.addAttribute("likes", likes);
		Integer plazasLibres = event.getCapacity() - likes;
		model.addAttribute("plazasLibres", plazasLibres);
		return "GraphsEventsTemplate";
	}

	
	@GetMapping("/admin/graph-tables")
	public String graphTables(Model model) {
		model.addAttribute("nombre", "Admin");
		model.addAttribute("site", "GRAFICO");
		setHeader(model);
		List<TableReservation> reservations = trService.getAll();

		Integer numPC = 0;
		Integer numXBOX_ONE = 0;
		Integer numPS5 = 0;

		for (TableReservation tableReservation : reservations) {
			Optional<Tablegame> opttable = tService.getByid(tableReservation.getId_table());
			if (opttable.isPresent()) {
				Tablegame table = opttable.get();
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
		}
		model.addAttribute("numPC", numPC);
		model.addAttribute("numXBOX_ONE", numXBOX_ONE);
		model.addAttribute("numPS5", numPS5);
		return "GraphsTableTemplate";
	}

	@GetMapping("/admin/delete-reservation")
	public String borrarReserva(@RequestParam String id, Model model) {
		TableReservation reserva = trService.getByid(Long.parseLong(id));
		trService.delete(reserva);
		model.addAttribute("events", eService.getAllEvents());
		model.addAttribute("reservations", trService.getAll());
		return getAdmin(model);
	}

	@GetMapping("/admin/delete-event")
	public String borrarEvento(@RequestParam String id, Model model) {
		Event evento = eService.getByid(Long.parseLong(id));
		if (evento != null) {
			eService.delete(evento);
		}
		model.addAttribute("events", eService.getAllEvents());
		return getAdmin(model);
	}

	@GetMapping("/add-event")
	public String getEventAdder(Model model) {
		setHeader(model);
		if (this.userComponent.isAdmin()) {
			model.addAttribute("name", "Nombre del evento*");
			model.addAttribute("description", "Descripción del evento");
			model.addAttribute("labels", "SHOOTER/MOBA/MMO/PRESENTATION");
			model.addAttribute("capacity", "");
			model.addAttribute("date", "0000-00-00");
			model.addAttribute("site", "EVENTO+");
			return "EventCreatorTemplate";
		}
		return "redirect:/profile";
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {
		model.addAttribute("site", "ADMIN");
		setHeader(model);
		if (this.userComponent.isAdmin()) {
			model.addAttribute("name", "Admin");
			model.addAttribute("events", eService.getAllEvents());
			return "admin";
		}
		return "redirect:/profile";
	}

}
