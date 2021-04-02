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

	public Event getByid(Long id){
		return eventRepository.findByid(id);
	}

	public void delete(Event event){
		eventRepository.delete(event);
	}

	public void save(Event event){
		eventRepository.save(event);
	}

    public Event createNewEvent(String name, String description, MultipartFile file, MultipartFile file1,
        MultipartFile file2, MultipartFile file3,String date,Integer capacity) {
		Event event = new Event(name, description,date,"",capacity);
		
		if (!file.isEmpty()) {
			event.setBannerUrl(imageService.uploadImage(file));
		} else {
			event.setBannerUrl("/images/uploads/defaultEvent.jpg");
		}

        if (!file1.isEmpty()) {
			event.getGallery().add(imageService.uploadImage(file1));
		} else {
			event.getGallery().add("/images/uploads/defaultEvent.jpg");
		}

        if (!file2.isEmpty()) {
			event.getGallery().add(imageService.uploadImage(file2));
		} else {
			event.getGallery().add("/images/uploads/defaultEvent.jpg");
		}

        if (!file3.isEmpty()) {
			event.getGallery().add(imageService.uploadImage(file3));
		} else {
			event.getGallery().add("/images/uploads/defaultEvent.jpg");
		}
		
		eventRepository.save(event);
		return event;
	}

}
