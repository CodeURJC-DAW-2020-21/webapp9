package urjc.ugc.ultragamecenter.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.Components.UserComponent;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Repositories.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserService uService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserComponent uComponent;

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public Page<Event> getPageEvents(int pageNumber, int pageSize) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		return eventRepository.findAll(p);
	}

	public Event getByid(Long id) {
		return eventRepository.findByid(id);
	}

	public void delete(Event event) {
		eventRepository.delete(event);
	}

	public void save(Event event) {
		eventRepository.save(event);
	}

	public Event createNewEvent(String name, String description, MultipartFile file, MultipartFile[] filePack,
			String date, Integer capacity, String labels) {
		Event event = null;
		if (uComponent.isAdmin()) {
			event = new Event(name, description, date, "", capacity);
			if (file!=null && !file.isEmpty()) {
				event.setBannerUrl(imageService.uploadImage(file));
			} else {
				event.setBannerUrl("/images/uploads/defaultEvent.jpg");
			}
			for (MultipartFile image : filePack) {
				if (file!=null && !image.isEmpty()) {
					event.getGallery().add(imageService.uploadImage(image));
				} else {
					event.getGallery().add("/images/uploads/defaultEvent.jpg");
				}
			}
			for (String l : labels.split("/")) {
				event.putLavel(l);
			}
			eventRepository.save(event);
		}

		return event;
	}

	public Event updateEvent(Long id, String name, String description, String date, Integer capacity,
			MultipartFile image, MultipartFile[] filePack) {
		Event event = null;
		if (uComponent.isAdmin()) {
			event = eventRepository.findByid(id);
			if (event != null) {
				event.setName(name!=null ? name : event.getName());
				event.setDescription(description!=null ? description : event.getDescription());
				event.setDate(date!=null ? date : event.getDate().toString());
				event.setCapacity(capacity ==null ? capacity : event.getCapacity());
				eventRepository.save(event);
				if (image != null) {
					event.setBannerUrl(imageService.uploadImage(image));
				}
				if (filePack != null) {
					for (MultipartFile file : filePack) {
						if (!file.isEmpty()) {
							event.getGallery().add(imageService.uploadImage(file));
						} else {
							event.getGallery().add("/images/uploads/defaultEvent.jpg");
						}
					}
				}
			}
		}
		return event;
	}

	public Event deleteID(Long id) {
		Event e = null;
		if (uComponent.isAdmin()) {
			e = getByid(id);
			if (e != null) {
				e.setDescription("Este evento se ha eliminado satisfactoriamiente");
				eventRepository.delete(e);
			}
		}
		return e;
	}

	public boolean like(Long id) {
		Event event = getByid(id);
		if (event != null && this.uComponent.isLoggedUser() && !this.uComponent.hasLiked(event.getId())) {
			this.uComponent.like(event, getAllEvents());
			save(event);
			uService.save(this.uComponent.getLoggedUser());
			return true;
		}
		return false;
	}

}
