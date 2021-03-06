package urjc.ugc.ultragamecenter.services;

import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.repositories.UserRepository;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eService;

    @Autowired
    UserDetailsServiceImpl uDetails;

    @Autowired
    UserService uService;

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get(): null;
    }

    public User createNewUser(String name, String lastName, String password, String email) throws IOException {
        User user = null;
        if (!userRepository.findByEmail(email).isPresent()) {
            user = new User(name, lastName, password, email);
            userRepository.save(user);
        }
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Long count() {
        return userRepository.count();
    }

    public User updateUser(String name, String lastName, MultipartFile image) {
        if (name == null) {
            name = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        User user = null;
        user = uDetails.getLogedUser();
        if (image != null && !image.isEmpty()) {
            imageService.uploadImage(image,user.getEmail());
        }
        user.setName(!name.equals("") ? name : user.getName());
        user.setLastName(!lastName.equals("") ? lastName : user.getLastName());
        userRepository.save(user);
        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User updateUserPassword(String password, String newPassword) {
        User user = null;
        user = uDetails.getLogedUser();
        if (user.matchPasword(password)) {
            user.setPassword(newPassword);
            save(user);
        } else {
            user = null;
        }
        return user;

    }

    public List<Event> getRecomendatedEvents(Integer i) {
        ArrayList<Event> recomendated = new ArrayList<>();
        ArrayList<Event> possibleEvents = (ArrayList<Event>) uDetails.getLogedUser().sort(eService.getAllEvents(), 100);
        for (Event e : possibleEvents) {
            if (!uDetails.getLogedUser().hasLiked(e.getId())) {
                recomendated.add(e);
            }
        }
        return recomendated.subList(0, Math.min(i,recomendated.size()));
    }

}
