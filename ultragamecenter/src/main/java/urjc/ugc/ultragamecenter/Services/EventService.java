package urjc.ugc.ultragamecenter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.repositories.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ImageService imageService;

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

	public Event createNewEvent(String name, String description, MultipartFile image,MultipartFile[] filePack, String date, Integer capacity, String labels) {
		Event event = new Event(name, description, date, "", capacity);
		for(MultipartFile file:filePack){
			if (!file.isEmpty()) {
				event.getGallery().add(imageService.uploadImage(file));
			} else {
				event.getGallery().add("/images/uploads/defaultEvent.jpg");
			}
		}
		if (!image.isEmpty()) {
			event.setBannerUrl(imageService.uploadImage(image));
		} else {
			event.setBannerUrl("/images/uploads/defaultEvent.jpg");
		}
		for (String label : labels.split("/")) {
			event.putLavel(label);
		}
		eventRepository.save(event);
		return event;
	}

	public Event updateEvent(Long id, String name, String description, String date, Integer capacity) {
        Event event = eventRepository.findByid(id);
		event.setName(name.equals("") ? event.getName():name);
		event.setDescription(description.equals("")? event.getDescription():description);
		event.setDate(date.equals("")?   event.getDate().toString():date);
		event.setCapacity(capacity== 0 ? event.getCapacity():capacity);
		eventRepository.save(event);
		return event;
    }

	public Event createNewEvent(String name, String description, String date, Integer capacity) {
		Event event = new Event(name, description, date, "", capacity);
		eventRepository.save(event);
		return event;
	}

	public void deleteID(String id) {
		Event evento = eventRepository.findByid(Long.parseLong(id));
		if (evento != null) {
			eventRepository.delete(evento);
		}
	}

}
