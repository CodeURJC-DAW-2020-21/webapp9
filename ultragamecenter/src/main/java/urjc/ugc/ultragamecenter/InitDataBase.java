package urjc.ugc.ultragamecenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import urjc.ugc.ultragamecenter.Repositories.EventRepository;
import urjc.ugc.ultragamecenter.Repositories.UserRepository;
import urjc.ugc.ultragamecenter.Repositories.TableRepository;
import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Models.UserEntity;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.Tablegame;
import urjc.ugc.ultragamecenter.Types.RoleType;


@Controller
public class InitDataBase implements CommandLineRunner{
    @Autowired
	private UserRepository userRepository; 
    @Autowired
	private EventRepository eventRepository;
    @Autowired
	private TableRepository tableRepository;
    
    @Override
	public void run(String... arg) {
        if (userRepository.count()==0){
            //create users
            UserEntity user0 = new UserEntity("DAW", "G9", "DAW", "daw@gamail.com");
            user0.setRoles(RoleType.ADMIN);
        
            UserEntity user1 = new UserEntity("Jesus", "Elez", "1234", "jesus@gmail.com");
            
            UserEntity user2 = new UserEntity("Miguel", "Cendrero", "1234", "miguel@gmail.com");
            
            UserEntity user3 = new UserEntity("Rodri", "Diez", "1234", "rodri10@gmil.com");
        
            UserEntity user4 = new UserEntity("Alex", "Fernandez", "1234", "alex@gmail.com");
            
            UserEntity user5 = new UserEntity("Rodri", "De Siqueira", "1234", "rodri@gmail.com");
            
            
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
            event1.putLavel(EventLavelType.MOBA.toString());
            event1.putLavel(EventLavelType.SHOOTER.toString());

            Event event2 = new Event("LOL", "championship", "2015-03-20", "banner2",100);
            event2.putLavel(EventLavelType.MOBA.toString());
            event2.putLavel(EventLavelType.SHOOTER.toString());

            Event event3 = new Event("COD", "black ops", "2015-03-25", "Hola",100);
            event3.putLavel(EventLavelType.MOBA.toString());
            event3.putLavel(EventLavelType.SHOOTER.toString());

            //save events
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
        }

        if(tableRepository.count()==0){
            //create xbox tables
            Tablegame xboxTable1= new Tablegame("XBOX_ONE");
            Tablegame xboxTable2= new Tablegame("XBOX_ONE");
            Tablegame xboxTable3= new Tablegame("XBOX_ONE");
            Tablegame xboxTable4= new Tablegame("XBOX_ONE");
            Tablegame xboxTable5= new Tablegame("XBOX_ONE");
            //save xbox tables
            tableRepository.save(xboxTable1);
            tableRepository.save(xboxTable2);
            tableRepository.save(xboxTable3);
            tableRepository.save(xboxTable4);
            tableRepository.save(xboxTable5);

            //create pc tables
            Tablegame pcTable1= new Tablegame("PC");
            Tablegame pcTable2= new Tablegame("PC");
            Tablegame pcTable3= new Tablegame("PC");
            Tablegame pcTable4= new Tablegame("PC");
            Tablegame pcTable5= new Tablegame("PC");
            //save pc tables
            tableRepository.save(pcTable1);
            tableRepository.save(pcTable2);
            tableRepository.save(pcTable3);
            tableRepository.save(pcTable4);
            tableRepository.save(pcTable5);

            //create ps tables
            Tablegame psTable1= new Tablegame("PS5");
            Tablegame psTable2= new Tablegame("PS5");
            Tablegame psTable3= new Tablegame("PS5");
            Tablegame psTable4= new Tablegame("PS5");
            Tablegame psTable5= new Tablegame("PS5");
            //save ps tables
            tableRepository.save(psTable1);
            tableRepository.save(psTable2);
            tableRepository.save(psTable3);
            tableRepository.save(psTable4);
            tableRepository.save(psTable5);
        }
        
    } 
}
