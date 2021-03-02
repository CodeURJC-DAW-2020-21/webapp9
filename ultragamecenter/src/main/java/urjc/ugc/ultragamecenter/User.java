package urjc.ugc.ultragamecenter;
import java.util.ArrayList;
public class User {
    private Long id;
    private String name;
    private String passwordHash;
    private String lastname;
    private String email;
    private Boolean emailVerified;
    private Boolean logged;
    private ArrayList<Event> eventsLikeIt;
    private ArrayList<Event> eventsJoinIt;
    private ArrayList<Table> reservatedTables;
    private String rol;
    private final String possibleRols[]={"Admin","User","NonRegisteredUser"}
}
