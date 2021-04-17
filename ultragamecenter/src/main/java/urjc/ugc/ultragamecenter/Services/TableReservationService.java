package urjc.ugc.ultragamecenter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.models.Tablegame;
import urjc.ugc.ultragamecenter.repositories.TableReservationRepository;

@Service
public class TableReservationService {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    UserComponent userComponent;

    @Autowired
    TableService tService;

    @Autowired
    UserService uService;

    @Autowired
    private TableReservationRepository trrepository;

    public List<TableReservation> getAll() {
        return trrepository.findAll();
    }

    public TableReservation getByid(Long id) {
        return trrepository.findByid(id);
    }

    public void delete(TableReservation reservation) {
        trrepository.delete(reservation);
    }

    public void save(TableReservation reservation) {
        trrepository.save(reservation);
    }

    public void delete(Long id) {
        trrepository.deleteById(Math.toIntExact(id));
    }

    public Object[] getReserved(Integer hourInt, String type, String day) {
        Long tableId = 0L;
        java.sql.Date sqldate = java.sql.Date.valueOf(day);
        Object[] o = new Object[2];
        List<Tablegame> tables = tService.getByTypeAndDate(type, sqldate);
        boolean reserved = false;
        int i = 0;
        while (!reserved && (i != tables.size())) {
            if (tables.get(i).getState().get(hourInt) == 0) {
                tables.get(i).setState(hourInt, 1);
                tableId = tables.get(i).getId();
                tService.saveAll(tables);
                reserved = true;
            }
            i++;
        }
        o[0] = reserved;
        o[1] = tableId;
        return o;
    }

    public TableReservation reserve(String email, Long tableId, Integer hourInt) {
        TableReservation tReserve = null;
        String randomCode = randomRefCode();
        if (this.userComponent.isLoggedUser()) {// logged user
            this.userComponent.getLoggedUser().addReferencedCode(randomCode);
            uService.save(this.userComponent.getLoggedUser());
            tReserve = new TableReservation(tableId, randomCode, hourInt);
            save(tReserve);
        } else { // guest user
            if (!email.equals("")) {
                tReserve = new TableReservation(tableId, randomCode, hourInt);
                save(tReserve);
                this.sendMail(email, randomCode);
            }
        }
        return tReserve;
    }

    private String randomRefCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (random.nextInt() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();

    }

    public void sendMail(String to, String code) {
        EmailSenderService emailSender = (EmailSenderService) appContext.getBean("emailSenderService");
        emailSender.sendEmail(to, code);
    }

    public TableReservation reserveTable(String type, String day, String hour, String email) {
        Integer hourInt = Integer.parseInt(hour);
        Object[] o = getReserved(hourInt, type, day);
        boolean reserved = (Boolean) o[0];
        Long tableId = (Long) o[1];
        if (reserved) {
            return reserve(email, tableId, hourInt);
        }
        return null;
    }

    public List<String> getMyReservations() {
        if(userComponent.isLoggedUser()){
            return userComponent.getLoggedUser().getReferencedCodes();
        }
        return new ArrayList<>();
       
    }
}
