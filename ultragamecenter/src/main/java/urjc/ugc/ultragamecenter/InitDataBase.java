package urjc.ugc.ultragamecenter;

import java.io.IOException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import urjc.ugc.ultragamecenter.repositories.*;
import urjc.ugc.ultragamecenter.models.*;

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
            user0.setRoles("ADMIN");
        
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
            event1.putLavel("SHOOTER");
            event1.setGallery("/images/Events/fornite1.jpeg","/images/Events/fornite2.jpg", "/images/Events/fornite3.jpg");

            Event event2 = new Event("LOL", "Final del mundial 2020.", "2021-04-10", "/images/Events/LOL0.png",150);
            event2.putLavel("MOBA");
            event2.setGallery("/images/Events/LOL1.png","/images/Events/LOL2.jpg", "/images/Events/LOL3.jpg");

            Event event3 = new Event("NVIDIA", "Nueva Gráfica super potente.", "2021-04-16", "/images/Events/NVIDIA0.jpg",50);
            event3.putLavel("PRESENTATION");
            event3.setGallery("/images/Events/NVIDIA1.jpg","/images/Events/NVIDIA2.jpg", "/images/Events/NVIDIA1.jpg");

            Event event4 = new Event("COD", "Ven a jugar con tus amigos a los mejores shooter de la franquicia.", "2021-03-25", "/images/Events/cod0.jpg",100);
            event4.putLavel("FPS");
            event4.setGallery("/images/Events/cod1.jpg","/images/Events/COD2.jpg", "/images/Events/COD3.jpg");

            Event event5 = new Event("HALO", "Presentacion de nuevo Halo 6", "2021-03-27", "/images/Events/halo0.jpg",100);
            event5.putLavel("SHOOTER");
            event5.putLavel("FPS");
            event5.setGallery("/images/Events/halo1.jpg","/images/Events/halo2.jpg", "/images/Events/halo3.jpg");
            
            Event event6 = new Event("Smite", "Final internacional entre europa y USA", "2021-04-25", "/images/Events/smite0.jpg",100);
            event6.putLavel("MOBA");
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
            //22
            Tablegame xboxTable1= new Tablegame("XBOX_ONE","2021-03-22");
            Tablegame xboxTable2= new Tablegame("XBOX_ONE","2021-03-22");
            Tablegame xboxTable3= new Tablegame("XBOX_ONE","2021-03-22");
            Tablegame xboxTable4= new Tablegame("XBOX_ONE","2021-03-22");
            Tablegame xboxTable5= new Tablegame("XBOX_ONE","2021-03-22");
            //23
            Tablegame xboxTable6= new Tablegame("XBOX_ONE","2021-03-23");
            Tablegame xboxTable7= new Tablegame("XBOX_ONE","2021-03-23");
            Tablegame xboxTable8= new Tablegame("XBOX_ONE","2021-03-23");
            Tablegame xboxTable9= new Tablegame("XBOX_ONE","2021-03-23");
            Tablegame xboxTable10= new Tablegame("XBOX_ONE","2021-03-23");
            //24
            Tablegame xboxTable11= new Tablegame("XBOX_ONE","2021-03-24");
            Tablegame xboxTable12= new Tablegame("XBOX_ONE","2021-03-24");
            Tablegame xboxTable13= new Tablegame("XBOX_ONE","2021-03-24");
            Tablegame xboxTable14= new Tablegame("XBOX_ONE","2021-03-24");
            Tablegame xboxTable15= new Tablegame("XBOX_ONE","2021-03-24");
            //25
            Tablegame xboxTable16= new Tablegame("XBOX_ONE","2021-03-25");
            Tablegame xboxTable17= new Tablegame("XBOX_ONE","2021-03-25");
            Tablegame xboxTable18= new Tablegame("XBOX_ONE","2021-03-25");
            Tablegame xboxTable19= new Tablegame("XBOX_ONE","2021-03-25");
            Tablegame xboxTable20= new Tablegame("XBOX_ONE","2021-03-25");
            //26
            Tablegame xboxTable21= new Tablegame("XBOX_ONE","2021-03-26");
            Tablegame xboxTable22= new Tablegame("XBOX_ONE","2021-03-26");
            Tablegame xboxTable23= new Tablegame("XBOX_ONE","2021-03-26");
            Tablegame xboxTable24= new Tablegame("XBOX_ONE","2021-03-26");
            Tablegame xboxTable25= new Tablegame("XBOX_ONE","2021-03-26");
            //27
            Tablegame xboxTable26= new Tablegame("XBOX_ONE","2021-03-27");
            Tablegame xboxTable27= new Tablegame("XBOX_ONE","2021-03-27");
            Tablegame xboxTable28= new Tablegame("XBOX_ONE","2021-03-27");
            Tablegame xboxTable29= new Tablegame("XBOX_ONE","2021-03-27");
            Tablegame xboxTable30= new Tablegame("XBOX_ONE","2021-03-27");
            //28
            Tablegame xboxTable31= new Tablegame("XBOX_ONE","2021-03-28");
            Tablegame xboxTable32= new Tablegame("XBOX_ONE","2021-03-28");
            Tablegame xboxTable33= new Tablegame("XBOX_ONE","2021-03-28");
            Tablegame xboxTable34= new Tablegame("XBOX_ONE","2021-03-28");
            Tablegame xboxTable35= new Tablegame("XBOX_ONE","2021-03-28");
            
            //save xbox tables
            tableRepository.save(xboxTable1);
            tableRepository.save(xboxTable2);
            tableRepository.save(xboxTable3);
            tableRepository.save(xboxTable4);
            tableRepository.save(xboxTable5);
            tableRepository.save(xboxTable6);
            tableRepository.save(xboxTable7);
            tableRepository.save(xboxTable8);
            tableRepository.save(xboxTable9);
            tableRepository.save(xboxTable10);
            tableRepository.save(xboxTable11);
            tableRepository.save(xboxTable12);
            tableRepository.save(xboxTable13);
            tableRepository.save(xboxTable14);
            tableRepository.save(xboxTable15);
            tableRepository.save(xboxTable16);
            tableRepository.save(xboxTable17);
            tableRepository.save(xboxTable18);
            tableRepository.save(xboxTable19);
            tableRepository.save(xboxTable20);
            tableRepository.save(xboxTable21);
            tableRepository.save(xboxTable22);
            tableRepository.save(xboxTable23);
            tableRepository.save(xboxTable24);
            tableRepository.save(xboxTable25);
            tableRepository.save(xboxTable26);
            tableRepository.save(xboxTable27);
            tableRepository.save(xboxTable28);
            tableRepository.save(xboxTable29);
            tableRepository.save(xboxTable30);
            tableRepository.save(xboxTable31);
            tableRepository.save(xboxTable32);
            tableRepository.save(xboxTable33);
            tableRepository.save(xboxTable34);
            tableRepository.save(xboxTable35);
            //create pc tables
            Tablegame pcTable1= new Tablegame("PC","2021-03-22");
            Tablegame pcTable2= new Tablegame("PC","2021-03-22");
            Tablegame pcTable3= new Tablegame("PC","2021-03-22");
            Tablegame pcTable4= new Tablegame("PC","2021-03-22");
            Tablegame pcTable5= new Tablegame("PC","2021-03-22");
            Tablegame pcTable6= new Tablegame("PC","2021-03-23");
            Tablegame pcTable7= new Tablegame("PC","2021-03-23");
            Tablegame pcTable8= new Tablegame("PC","2021-03-23");
            Tablegame pcTable9= new Tablegame("PC","2021-03-23");
            Tablegame pcTable10= new Tablegame("PC","2021-03-23");
            Tablegame pcTable11= new Tablegame("PC","2021-03-24");
            Tablegame pcTable12= new Tablegame("PC","2021-03-24");
            Tablegame pcTable13= new Tablegame("PC","2021-03-24");
            Tablegame pcTable14= new Tablegame("PC","2021-03-24");
            Tablegame pcTable15= new Tablegame("PC","2021-03-24");
            Tablegame pcTable16= new Tablegame("PC","2021-03-25");
            Tablegame pcTable17= new Tablegame("PC","2021-03-25");
            Tablegame pcTable18= new Tablegame("PC","2021-03-25");
            Tablegame pcTable19= new Tablegame("PC","2021-03-25");
            Tablegame pcTable20= new Tablegame("PC","2021-03-25");
            Tablegame pcTable21= new Tablegame("PC","2021-03-26");
            Tablegame pcTable22= new Tablegame("PC","2021-03-26");
            Tablegame pcTable23= new Tablegame("PC","2021-03-26");
            Tablegame pcTable24= new Tablegame("PC","2021-03-26");
            Tablegame pcTable25= new Tablegame("PC","2021-03-26");
            Tablegame pcTable26= new Tablegame("PC","2021-03-27");
            Tablegame pcTable27= new Tablegame("PC","2021-03-27");
            Tablegame pcTable28= new Tablegame("PC","2021-03-27");
            Tablegame pcTable29= new Tablegame("PC","2021-03-27");
            Tablegame pcTable30= new Tablegame("PC","2021-03-27");
            Tablegame pcTable31= new Tablegame("PC","2021-03-28");
            Tablegame pcTable32= new Tablegame("PC","2021-03-28");
            Tablegame pcTable33= new Tablegame("PC","2021-03-28");
            Tablegame pcTable34= new Tablegame("PC","2021-03-28");
            Tablegame pcTable35= new Tablegame("PC","2021-03-28");
            //save pc tables
            tableRepository.save(pcTable1);
            tableRepository.save(pcTable2);
            tableRepository.save(pcTable3);
            tableRepository.save(pcTable4);
            tableRepository.save(pcTable5);
            tableRepository.save(pcTable6);
            tableRepository.save(pcTable7);
            tableRepository.save(pcTable8);
            tableRepository.save(pcTable9);
            tableRepository.save(pcTable10);
            tableRepository.save(pcTable11);
            tableRepository.save(pcTable12);
            tableRepository.save(pcTable13);
            tableRepository.save(pcTable14);
            tableRepository.save(pcTable15);
            tableRepository.save(pcTable16);
            tableRepository.save(pcTable17);
            tableRepository.save(pcTable18);
            tableRepository.save(pcTable19);
            tableRepository.save(pcTable20);
            tableRepository.save(pcTable21);
            tableRepository.save(pcTable22);
            tableRepository.save(pcTable23);
            tableRepository.save(pcTable24);
            tableRepository.save(pcTable25);
            tableRepository.save(pcTable26);
            tableRepository.save(pcTable27);
            tableRepository.save(pcTable28);
            tableRepository.save(pcTable29);
            tableRepository.save(pcTable30);
            tableRepository.save(pcTable31);
            tableRepository.save(pcTable32);
            tableRepository.save(pcTable33);
            tableRepository.save(pcTable34);
            tableRepository.save(pcTable35);

            //create ps tables
            Tablegame psTable1= new Tablegame("PS5","2021-03-22");
            Tablegame psTable2= new Tablegame("PS5","2021-03-22");
            Tablegame psTable3= new Tablegame("PS5","2021-03-22");
            Tablegame psTable4= new Tablegame("PS5","2021-03-22");
            Tablegame psTable5= new Tablegame("PS5","2021-03-22");
            Tablegame psTable6= new Tablegame("PS5","2021-03-23");
            Tablegame psTable7= new Tablegame("PS5","2021-03-23");
            Tablegame psTable8= new Tablegame("PS5","2021-03-23");
            Tablegame psTable9= new Tablegame("PS5","2021-03-23");
            Tablegame psTable10= new Tablegame("PS5","2021-03-23");
            Tablegame psTable11= new Tablegame("PS5","2021-03-24");
            Tablegame psTable12= new Tablegame("PS5","2021-03-24");
            Tablegame psTable13= new Tablegame("PS5","2021-03-24");
            Tablegame psTable14= new Tablegame("PS5","2021-03-24");
            Tablegame psTable15= new Tablegame("PS5","2021-03-24");
            Tablegame psTable16= new Tablegame("PS5","2021-03-25");
            Tablegame psTable17= new Tablegame("PS5","2021-03-25");
            Tablegame psTable18= new Tablegame("PS5","2021-03-25");
            Tablegame psTable19= new Tablegame("PS5","2021-03-25");
            Tablegame psTable20= new Tablegame("PS5","2021-03-25");
            Tablegame psTable21= new Tablegame("PS5","2021-03-26");
            Tablegame psTable22= new Tablegame("PS5","2021-03-26");
            Tablegame psTable23= new Tablegame("PS5","2021-03-26");
            Tablegame psTable24= new Tablegame("PS5","2021-03-26");
            Tablegame psTable25= new Tablegame("PS5","2021-03-26");
            Tablegame psTable26= new Tablegame("PS5","2021-03-27");
            Tablegame psTable27= new Tablegame("PS5","2021-03-27");
            Tablegame psTable28= new Tablegame("PS5","2021-03-27");
            Tablegame psTable29= new Tablegame("PS5","2021-03-27");
            Tablegame psTable30= new Tablegame("PS5","2021-03-27");
            Tablegame psTable31= new Tablegame("PS5","2021-03-28");
            Tablegame psTable32= new Tablegame("PS5","2021-03-28");
            Tablegame psTable33= new Tablegame("PS5","2021-03-28");
            Tablegame psTable34= new Tablegame("PS5","2021-03-28");
            Tablegame psTable35= new Tablegame("PS5","2021-03-28");
            //save ps tables
            tableRepository.save(psTable1);
            tableRepository.save(psTable2);
            tableRepository.save(psTable3);
            tableRepository.save(psTable4);
            tableRepository.save(psTable5);
            tableRepository.save(psTable6);
            tableRepository.save(psTable7);
            tableRepository.save(psTable8);
            tableRepository.save(psTable9);
            tableRepository.save(psTable10);
            tableRepository.save(psTable11);
            tableRepository.save(psTable12);
            tableRepository.save(psTable13);
            tableRepository.save(psTable14);
            tableRepository.save(psTable15);
            tableRepository.save(psTable16);
            tableRepository.save(psTable17);
            tableRepository.save(psTable18);
            tableRepository.save(psTable19);
            tableRepository.save(psTable20);
            tableRepository.save(psTable21);
            tableRepository.save(psTable22);
            tableRepository.save(psTable23);
            tableRepository.save(psTable24);
            tableRepository.save(psTable25);
            tableRepository.save(psTable26);
            tableRepository.save(psTable27);
            tableRepository.save(psTable28);
            tableRepository.save(psTable29);
            tableRepository.save(psTable30);
            tableRepository.save(psTable31);
            tableRepository.save(psTable32);
            tableRepository.save(psTable33);
            tableRepository.save(psTable34);
            tableRepository.save(psTable35);
           
        }
        
    } 

    public void setEventImage(Event event, String classpathResource) throws IOException {
		Resource image = new ClassPathResource(classpathResource);
		event.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}
}
