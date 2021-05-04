package urjc.ugc.ultragamecenter.services;

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
    public void run(String... arg) throws IOException {

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
            Event event = new Event("Fornite", "Vive la colaboración entre Fortnite y Marvel.", "2021-03-21", 125);
            event.putLabel("SHOOTER");
            event.setID(0L);
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);

            event = new Event("LOL", "Final del mundial 2020.", "2021-04-10", 150);
            event.putLabel("MOBA");
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);

            event = new Event("NVIDIA", "Nueva Gráfica super potente.", "2021-04-16", 50);
            event.putLabel("PRESENTATION");
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);

            event = new Event("COD", "Ven a jugar con tus amigos a los mejores shooter de la franquicia.", "2021-03-25", 100);
            event.putLabel("FPS");
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);

            event = new Event("HALO", "Presentacion de nuevo Halo 6", "2021-03-27", 100);
            event.putLabel("SHOOTER");
            event.putLabel("FPS");
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);

            event = new Event("Smite", "Final internacional entre europa y USA", "2021-04-25", 100);
            event.putLabel("MOBA");
            ImageService.CREATE_FOLDER_EVENT(event.getName());
            eventRepository.save(event);
        }

        if (tableRepository.count() == 0) {

            // create ps tables
            for (int i = 3; i < 13; i++) {
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
