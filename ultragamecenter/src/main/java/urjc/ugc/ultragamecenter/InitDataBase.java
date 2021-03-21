package urjc.ugc.ultragamecenter;

import java.io.IOException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import urjc.ugc.ultragamecenter.Repositories.EventRepository;
import urjc.ugc.ultragamecenter.Repositories.UserRepository;
import urjc.ugc.ultragamecenter.Repositories.TableRepository;
import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Models.User;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.Tablegame;
import urjc.ugc.ultragamecenter.Types.RoleType;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


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
            User user0 = new User("DAW", "G9", "DAW", "daw@gmail.com");
            user0.setRoles(RoleType.ADMINISTRATOR);
        
            User user1 = new User("Jesus", "Elez", "1234", "jesus@gmail.com");
            
            User user2 = new User("Miguel", "Cendrero", "1234", "miguel@gmail.com");
            
            User user3 = new User("Rodri", "Diez", "1234", "rodri10@gmail.com");
        
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
            Event event1 = new Event("Fornite", "Vive la colaboración entre Fortnite y Marvel.", "2021-03-21", "/images/Events/fornite0.jpg",125);
            event1.putLavel(EventLavelType.SHOOTER.toString());
            event1.setGallery("/images/Events/fornite1.jpeg","/images/Events/fornite2.jpg", "/images/Events/fornite3.jpg");

            Event event2 = new Event("LOL", "Final del mundial 2020.", "2021-04-10", "/images/Events/LOL0.png",150);
            event2.putLavel(EventLavelType.MOBA.toString());
            event2.setGallery("/images/Events/LOL1.png","/images/Events/LOL2.jpg", "/images/Events/LOL3.jpg");

            Event event3 = new Event("NVIDIA", "Nueva Gráfica super potente.", "2021-04-16", "/images/Events/NVIDIA0.jpg",50);
            event3.putLavel(EventLavelType.PRESENTATION.toString());
            event3.setGallery("/images/Events/NVIDIA1.jpg","/images/Events/NVIDIA2.jpg", "/images/Events/NVIDIA1.jpg");

            Event event4 = new Event("COD", "Ven a jugar con tus amigos a los mejores shooter de la franquicia.", "2021-03-25", "/images/Events/cod0.jpg",100);
            event4.putLavel(EventLavelType.FPS.toString());
            event4.setGallery("/images/Events/cod1.jpg","/images/Events/COD2.jpg", "/images/Events/COD3.jpg");

            Event event5 = new Event("HALO", "Presentacion de nuevo Halo 6", "2021-03-27", "/images/Events/halo0.jpg",100);
            event5.putLavel(EventLavelType.SHOOTER.toString());
            event5.putLavel(EventLavelType.FPS.toString());
            event5.setGallery("/images/Events/halo1.jpg","/images/Events/halo2.jpg", "/images/Events/halo3.jpg");
            
            Event event6 = new Event("Smite", "Final internacional entre europa y USA", "2021-04-25", "/images/Events/smite0.jpg",100);
            event6.putLavel(EventLavelType.MOBA.toString());
            event6.setGallery("/images/Events/smite1.jpg","/images/Events/smite2.png","/images/Events/smite3.jpg");

        
            //save events
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
            eventRepository.save(event4);
            eventRepository.save(event5);
            eventRepository.save(event6);
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

    public void setEventImage(Event event, String classpathResource) throws IOException {
		Resource image = new ClassPathResource(classpathResource);
		event.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}
}
