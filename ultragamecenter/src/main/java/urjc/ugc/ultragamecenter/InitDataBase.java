package urjc.ugc.ultragamecenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import urjc.ugc.ultragamecenter.Repositories.EventRepository;
import urjc.ugc.ultragamecenter.Repositories.UserRepository;
import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Models.User;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Types.RoleType;


@Controller
public class InitDataBase implements CommandLineRunner{
    @Autowired
	private UserRepository userRepository; 
    @Autowired
	private EventRepository eventRepository;
    
    @Override
	public void run(String... arg) {
        if (userRepository.count()==0){
            //create users
            User user0 = new User("DAW", "G9", "DAW", "daw@gamail.com");
            user0.setRoles(RoleType.ADMINISTRATOR);
        
            User user1 = new User("Jesus", "Elez", "1234", "jesus@gmail.com");
            
            User user2 = new User("Miguel", "Cendrero", "1234", "miguel@gmail.com");
            
            User user3 = new User("Rodri", "Diez", "1234", "rodri10@gmil.com");
        
            User user4 = new User("Alex", "Fernandez", "1234", "alex@gmail.com");
            
            User user5 = new User("Rodri", "De Siqueira", "1234", "rodri@gmail.com");
            
            
            //save users
            userRepository.save(user0);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
        }

        if(eventRepository.count()==0){
            //create events
            Event event1 = new Event("Fornite", "fornite x marvel", "2015-03-15", "banner1",100);
            event1.putLavel(EventLavelType.MOBA);
            event1.putLavel(EventLavelType.SHOOTER);

            Event event2 = new Event("LOL", "championship", "2015-03-20", "banner2",100);
            event2.putLavel(EventLavelType.MOBA);
            event2.putLavel(EventLavelType.SHOOTER);

            Event event3 = new Event("COD", "black ops", "2015-03-25", "Hola",200);
            event3.putLavel(EventLavelType.MOBA);
            event3.putLavel(EventLavelType.SHOOTER);

            //save events
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
        }
        
    } 
}
