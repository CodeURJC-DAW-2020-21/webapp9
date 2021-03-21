package urjc.ugc.ultragamecenter.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Repositories.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
	private ImageService imageService;

    public Event createNewEvent(String name, String description, MultipartFile file,
                                String date,Integer capacity) {
		Event event = new Event(name, description,date,"",capacity);
		
		if (!file.isEmpty()) {
			event.setBannerUrl(imageService.uploadImage(file));
		} else {
			event.setBannerUrl("images/uploads/defaultEvent.png");
		}
		
		eventRepository.save(event);
		return event;
	}
}
