package urjc.ugc.ultragamecenter.Controllers;
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
		
		
		User user = new User( name,  passwordHash, surname, email, "User");
		
		urepository.save(user);
		
		
		
		return "register";

	}*/
	
	/*@GetMapping("/users")
	public Iterable<User> listUsers() {
	
		return userRepo.findAll();
	}
	*/
	@GetMapping("/login")
	public String login(Model model, HttpSession sesion) {
		
		return "register";
	}
	
		


}
