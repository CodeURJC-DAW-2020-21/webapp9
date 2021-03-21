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
