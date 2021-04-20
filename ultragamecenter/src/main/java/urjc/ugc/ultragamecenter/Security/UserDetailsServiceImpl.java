package urjc.ugc.ultragamecenter.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.repositories.UserRepository;
import urjc.ugc.ultragamecenter.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService uService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar nombre de usuario en nuestra base de datos
        User appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login email not valid"));
        Set<GrantedAuthority> grantList = new HashSet<>();

        // Crear la lista de los roles/accessos que tienen el usuarios
        for (String role : appUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
            grantList.add(grantedAuthority);
        }
        

        // Crear y retornar Objeto de usuario soportado por Spring Security
        //new org.springframework.security.core.userdetails.User(user.getName(), user.getEncodedPassword(), roles);
        UserDetails user = new org.springframework.security.core.userdetails.User(appUser.getEmail(), appUser.getPassword(),grantList);
        return user;
    }

    public boolean isLoggedUserADMIN() {
        return loggedUserHasRole("ADMIN");
    }

    public boolean loggedUserHasRole(String role) {
        String role2= "ROLE_"+role;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null; 
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
            roles = loggedUser.getAuthorities().stream()
                .filter(x -> role2.equals(x.getAuthority() ))      
                .findFirst().orElse(null); 
        }
        return roles != null ?true :false;
       }

    public String getEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getLogedUser(){
        return uService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Boolean idLoggedUser(){
        return loggedUserHasRole("USER");
    }

}