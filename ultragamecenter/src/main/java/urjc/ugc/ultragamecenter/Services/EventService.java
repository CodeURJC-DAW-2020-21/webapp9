package urjc.ugc.ultragamecenter.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.repositories.EventRepository;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserService uService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserDetailsServiceImpl uDetails;

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

	public Event createNewEvent(String name, String description, MultipartFile file, MultipartFile[] filePack, String date, Integer capacity, String labels) throws IOException {
		Event event = null;
		event = new Event(name, description, date,capacity);
		giveImages(event, file, filePack);
		for (String l : labels.split("/")) {
			event.putLabel(l);
		}
		eventRepository.save(event);
		return event;
	}

	public void giveImages(Event event, MultipartFile file, MultipartFile[] filePack){
		if (file != null && !file.isEmpty()) {
			imageService.uploadImageEvent(file, event.getName(), 1);
		}
		if(filePack!=null){
			int auxNumber=0;
			for (MultipartFile image : filePack) {
				auxNumber++;
				if (image != null && !image.isEmpty()) {
					imageService.uploadImageEvent(image,event.getName(),auxNumber);
				}
			}
		}
	}

	public Event updateEvent(Long id, String name, String description, String date, Integer capacity,
			MultipartFile image, MultipartFile[] filePack) {
		Event event = null;
		event = eventRepository.findByid(id);
		if (event != null) {
			event.setName(name != null ? name : event.getName());
			event.setDescription(description != null ? description : event.getDescription());
			event.setDate(date != null ? date : event.getDate().toString());
			event.setCapacity(capacity == null ? capacity : event.getCapacity());
			giveImages(event, image, filePack);
			eventRepository.save(event);
		}
		return event;
	}

	public Event deleteID(Long id) {
		Event e = null;
		e = getByid(id);
		if (e != null) {
			e.setDescription("Este evento se ha eliminado satisfactoriamiente");
			eventRepository.delete(e);
		}
		return e;
	}

	public boolean like(Long id) {
		Event event = getByid(id);
		User u = uDetails.getLogedUser();
		if (!u.hasLiked(event.getId())) {
			u.likedEvent(event, getAllEvents());
			save(event);
			uService.save(u);
			return true;
		}
		return false;
	}

	public Collection<Event> transform(List<Long> eventsLiked) {
		ArrayList<Event> e = new ArrayList<>();
		for (Long l : eventsLiked) {
			e.add(getByid(l));
		}
		return e;
	}

}
