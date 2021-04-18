package urjc.ugc.ultragamecenter;

import java.io.IOException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import urjc.ugc.ultragamecenter.Repositories.*;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Controller
public class InitDataBase implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TableRepository tableRepository;

    public int maxDay(int i) {
        switch (i) {
        case 1:
            return 32;
        case 2:
            return 29;
        case 3:
            return 32;
        case 4:
            return 31;
        case 5:
            return 32;
        case 6:
            return 31;
        case 7:
            return 32;
        case 8:
            return 32;
        case 9:
            return 31;
        case 10:
            return 32;
        case 11:
            return 31;
        default:
            return 32;
        }
    }

    @Override
    public void run(String... arg) {

        if (userRepository.count() == 0) {
            // create users
            User user0 = new User("DAW", "G9", "DAW", "daw@gmail.com");
            user0.setRoles("ADMIN");

            User user1 = new User("Jesus", "Elez", "1234", "jesus@gmail.com");

            User user2 = new User("Miguel", "Cendrero", "1234", "miguel@gmail.com");

            User user3 = new User("Rodri", "Diez", "1234", "rodri10@gmail.com");

            User user4 = new User("Alex", "Fernandez", "1234", "alex@gmail.com");

            User user5 = new User("Rodri", "De Siqueira", "1234", "rodri@gmail.com");

            // save users
            userRepository.save(user0);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
        }

        if (eventRepository.count() == 0) {
            // create events
            Event event1 = new Event("Fornite", "Vive la colaboración entre Fortnite y Marvel.", "2021-03-21",
                    "/images/Events/fornite0.jpg", 125);
            event1.putLavel("SHOOTER");
            event1.setGallery("/images/Events/fornite1.jpeg", "/images/Events/fornite2.jpg",
                    "/images/Events/fornite3.jpg");

            Event event2 = new Event("LOL", "Final del mundial 2020.", "2021-04-10", "/images/Events/LOL0.png", 150);
            event2.putLavel("MOBA");
            event2.setGallery("/images/Events/LOL1.png", "/images/Events/LOL2.jpg", "/images/Events/LOL3.jpg");

            Event event3 = new Event("NVIDIA", "Nueva Gráfica super potente.", "2021-04-16",
                    "/images/Events/NVIDIA0.jpg", 50);
            event3.putLavel("PRESENTATION");
            event3.setGallery("/images/Events/NVIDIA1.jpg", "/images/Events/NVIDIA2.jpg", "/images/Events/NVIDIA1.jpg");

            Event event4 = new Event("COD", "Ven a jugar con tus amigos a los mejores shooter de la franquicia.",
                    "2021-03-25", "/images/Events/cod0.jpg", 100);
            event4.putLavel("FPS");
            event4.setGallery("/images/Events/cod1.jpg", "/images/Events/COD2.jpg", "/images/Events/COD3.jpg");

            Event event5 = new Event("HALO", "Presentacion de nuevo Halo 6", "2021-03-27", "/images/Events/halo0.jpg",
                    100);
            event5.putLavel("SHOOTER");
            event5.putLavel("FPS");
            event5.setGallery("/images/Events/halo1.jpg", "/images/Events/halo2.jpg", "/images/Events/halo3.jpg");

            Event event6 = new Event("Smite", "Final internacional entre europa y USA", "2021-04-25",
                    "/images/Events/smite0.jpg", 100);
            event6.putLavel("MOBA");
            event6.setGallery("/images/Events/smite1.jpg", "/images/Events/smite2.png", "/images/Events/smite3.jpg");

            // save events
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
            eventRepository.save(event4);
            eventRepository.save(event5);
            eventRepository.save(event6);
        }

        if (tableRepository.count() == 0) {

            // create ps tables
            for (int i = 4; i < 13; i++) {
                for (int x = 1; x < maxDay(i); x++) {
                    for (int z = 0; z < 5; z++) {
                        Tablegame t1 = new Tablegame("PS5", "2021-" + i + "-" + x);
                        Tablegame t2 = new Tablegame("PC", "2021-" + i + "-" + x);
                        Tablegame t3 = new Tablegame("XBOX_ONE", "2021-" + i + "-" + x);
                        tableRepository.save(t1);
                        tableRepository.save(t2);
                        tableRepository.save(t3);
                    }
                }
            }
        }
    }

    public void setEventImage(Event event, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        event.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
    }
}
