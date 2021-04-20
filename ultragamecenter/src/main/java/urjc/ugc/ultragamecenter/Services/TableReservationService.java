package urjc.ugc.ultragamecenter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.models.Tablegame;
import urjc.ugc.ultragamecenter.repositories.TableReservationRepository;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;

@Service
public class TableReservationService {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    TableService tService;

    @Autowired
    UserService uService;

    @Autowired
    private TableReservationRepository trrepository;

    @Autowired
    UserDetailsServiceImpl uDetails;

    public List<TableReservation> getAll() {
        return trrepository.findAll();
    }

    public Page<TableReservation> getPageReservations(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize);
        return trrepository.findAll(p);
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
        try {
            java.sql.Date sqldate = java.sql.Date.valueOf(day);
            Object[] o = new Object[2];
            ArrayList<Tablegame> tables = (ArrayList<Tablegame>) tService.getByTypeAndDate(type, sqldate);
            boolean reserved = false;
            int i = 0;
            if (hourInt > 8) {
                Object[] a = { null, null };
                return a;
            }
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
        } catch (java.lang.IllegalArgumentException aux) {
            Object[] a = { null, null };
            return a;
        }

    }

    public TableReservation reserve(String email, Long tableId, Integer hourInt) {
        TableReservation tReserve = null;
        String randomCode = randomRefCode();
        if (uDetails.idLoggedUser()) {// logged user
            uDetails.getLogedUser().addReferencedCode(randomCode);
            uService.save(uDetails.getLogedUser());
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
        if (o[0] == null) {
            return null;
        }
        boolean reserved = (Boolean) o[0];
        Long tableId = (Long) o[1];
        if (reserved) {
            return reserve(email, tableId, hourInt);
        }
        return null;
    }

    public List<String> getMyReservations() {
        return uDetails.getLogedUser().getReferencedCodes();

    }
}
