package urjc.ugc.ultragamecenter.Controllers;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.ugc.ultragamecenter.Models.*;
import urjc.ugc.ultragamecenter.Repositories.*;
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

	
    @GetMapping("/register")
    public String getLoginRegister(Model model) {
        model.addAttribute("nombre4", "Register Page");
        return "LoginRegisterTemplate";
    }
	
	/*@PostMapping("/add-user")
	public String addUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String telephone, @RequestParam String password, Model model) {
		
		//Encriptar el pasword ahí :D
		String passwordHash = password;
		
		String rol = AplicationController.getRol(email);
		
		
		User user = new User( name,  passwordHash, surname, email, rol);
		
		//Guardar el usuario aqui
		
		
		
		return "register";

	}*/
	
	/*@GetMapping("/users")
	public Iterable<User> listUsers() {
	
		return userRepo.findAll();
	}
	*/
	@GetMapping("/login")
	public String login(Map<String, Object> model, HttpSession sesion) {
		model.put("title", "Iniciar sesión");
		
		return "login";
	}
	
		


}

