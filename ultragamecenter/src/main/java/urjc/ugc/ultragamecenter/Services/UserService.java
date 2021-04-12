package urjc.ugc.ultragamecenter.services;

import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean existsById(int id) {
        return userRepository.existsById(Math.toIntExact(id));
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(int pageNumber, int rowPerPage) {
        List<User> users = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        userRepository.findAll(sortedByLastUpdateDesc).forEach(users::add);
        return users;
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

    public void update(User user) throws IOException {
        if (user.getName().equals("")) {
            throw new IOException("Name is required");
        }
        if (user.getEmail().equals("")) {
            throw new IOException("Email is required");
        }
        if (!existsById(Math.toIntExact(user.getId()))) {
            throw new IOException("Cannot find User with id: " + user.getId());
        }
        userRepository.save(user);
    }

    public void deleteById(Integer id) throws IOException {
        if (!existsById(Math.toIntExact(id))) {
            throw new IOException("Cannot find User with id: " + id);
        } else {
            userRepository.deleteById(Math.toIntExact(id));
        }
    }

    public Long count() {
        return userRepository.count();
    }

    public User updateUser(String email, String name, String lastName, String password, String emailNew) {
        User user = this.findByEmail(email);

        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(emailNew);
        userRepository.save(user);
        return user;
    }

    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}