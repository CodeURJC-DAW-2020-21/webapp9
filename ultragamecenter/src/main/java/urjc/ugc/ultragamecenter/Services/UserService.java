package urjc.ugc.ultragamecenter.Services;

import urjc.ugc.ultragamecenter.Models.UserEntity;
import urjc.ugc.ultragamecenter.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
 
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private boolean existsById(int id) {
        return userRepository.existsById(Math.toIntExact(id));
    }
    
    public UserEntity findById(Long id) {
        return userRepository.findByid(id);
    }
    
    public List<UserEntity> findAll(int pageNumber, int rowPerPage) {
        List<UserEntity> users = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage, 
                Sort.by("id").ascending());
        userRepository.findAll(sortedByLastUpdateDesc).forEach(users::add);
        return users;
    }
    
    public UserEntity save(UserEntity user) throws Exception {
        if (StringUtils.isEmpty(user.getName())) {
            throw new Exception("Name is required");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new Exception("Email is required");
        }
        if (user.getId() != null && existsById(Math.toIntExact(user.getId()))) { 
            throw new Exception("User with id: " + user.getId() + " already exists");
        }
        return userRepository.save(user);
    }
    
    public void update(UserEntity user) throws Exception {
        if (StringUtils.isEmpty(user.getName())) {
            throw new Exception("Name is required");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new Exception("Email is required");
        }
        if (!existsById(Math.toIntExact(user.getId()))) {
            throw new Exception("Cannot find User with id: " + user.getId());
        }
        userRepository.save(user);
    }
    
    public void deleteById(Integer id) throws Exception {
        if (!existsById(Math.toIntExact(id))) { 
            throw new Exception("Cannot find User with id: " + id);
        }
        else {
            userRepository.deleteById(Math.toIntExact(id));
        }
    }
    
    public Long count() {
        return userRepository.count();
    }
}