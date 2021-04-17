package urjc.ugc.ultragamecenter.services;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.repositories.UserRepository;

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
    private UserComponent uComponent;

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createNewUser(String name, String lastName, String password, String email) {
        User user = null;
        if (userRepository.findByEmail(email) == null) {
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
        User user = null;
        if (this.uComponent.isLoggedUser()) {
            user = uComponent.getLoggedUser();
            if (image !=null &&!image.isEmpty()) {
                user.setProfileSrc(imageService.uploadImage(image));
            } else {
                user.setProfileSrc("images/uploads/defaultuser.png");
            }
            user.setName(!name.equals("") ? name : user.getName());
            user.setLastName(!lastName.equals("") ? lastName : user.getLastName());
            userRepository.save(user);
        }
        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User updateUserPassword(String password, String newPassword) {
        User user = null;
        if (this.uComponent.isLoggedUser()) {
            user = uComponent.getLoggedUser();
            if (user.matchPasword(password)) {
                user.setPassword(newPassword);
                save(user);
            } else {
                user = null;
            }
        }
        return user;
    }

    public User logUsr(String email, String password){	
		User aux = findByEmail(email);
		if (aux != null && aux.matchPasword(password)) {
			uComponent.setLoggedUser(aux);
			return aux;
		}
		return null;
	}
}