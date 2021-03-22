package urjc.ugc.ultragamecenter.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import urjc.ugc.ultragamecenter.types.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import urjc.ugc.ultragamecenter.types.RoleType;

@Entity @EntityListeners(AuditingEntityListener.class)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 5767343013628002370L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String profileSrc;
    private String email;
    private ArrayList<Long> eventsLikeIt;
    private ArrayList<String> referencedCodes;
    private HashMap<String, Double> affinity;
    private ArrayList<Event> recomendated;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User(String name, String lastName, String password, String email) {
        super();
        this.name = name;
        this.profileSrc = "images/uploads/defaultuser.png";
        this.passwordHash = new BCryptPasswordEncoder().encode(password);
        this.lastName = lastName;
        this.email = email;
        this.roles = new ArrayList<String>();
        this.roles.add("USER");
    }

    public User() {

    }

    /*
     * public User(String name, String lastname, String password, String email) {
     * super(); this.name = name; this.lastName = lastname; this.passwordHash =
     * password; this.email = email; this.eventsLikeIt = new ArrayList<Long>();
     * this.reservatedTables = new ArrayList<Tablegame>(); this.roles = new
     * ArrayList<String>(); this.roles.add("USER"); }
     * 
     * public User(String name, String password, String email, String role) {
     * this.name = name; this.passwordHash = password; this.lastName = "";
     * this.email = email; this.eventsLikeIt = new ArrayList<Long>();
     * this.reservatedTables = new ArrayList<Tablegame>(); this.roles = new
     * ArrayList<String>(); this.roles.add(role); }
     */

    private Double getAffinity(Event e) {
        Double aux = 0.0;
        for (String lavel : e.getLavels()) {
            if (affinity.containsKey(lavel)) {
                aux += affinity.get(lavel);
            } else {
                affinity.put(lavel, 0.5);
            }
        }
        return aux;
    }

    public void refresh(Event e) {
        Double aux = getAffinity(e);
        Double comparator = getAffinity(recomendated.get(recomendated.size() - 1));
        if (comparator < aux) {
            recomendated.remove(recomendated.size() - 1);
            recomendated.add(e);
            recomendated.sort((e1, e2) -> (int) (100 * (getAffinity(e1) - getAffinity(e2))));
        }
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.passwordHash;
    }

    public ArrayList<Long> getEventsLiked() {
        return this.eventsLikeIt;
    }

    public void setEventLiked(Event event) {
        this.eventsLikeIt.add(event.getId());
    }

    public ArrayList<String> getReferencedCodes() {
        return this.referencedCodes;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(String role) {
        this.roles.add(role);
    }

    public void setReferencedCode(ArrayList<String> rc) {
        this.referencedCodes = rc;
    }

    public String getProfileSrc() {
        return profileSrc;
    }

    public void setProfileSrc(String src) {
        this.profileSrc = src;
    }

    public void giveRoles(String role) {
        if (this.roles.contains(role)) {
            return;
        } else {
            if (role == "ADMIN") {
                this.roles.remove("ADMIN");
                this.roles.add("USER");
            } else {
                this.roles.remove("USER");
                this.roles.add("ADMIN");
            }
        }
    }

    public void addReferencedCode(String refCode) {
        this.referencedCodes.add(refCode);
    }

    @Override
    public String toString() {
        return "User [id=" + this.id + ", Name=" + this.name + ", lastName=" + this.lastName + ", email=" + this.email
                + ", envets liked=" + this.eventsLikeIt + ", password=" + this.passwordHash + ", Referenced_Codes="
                + this.referencedCodes + ", role=" + this.roles + "]";

    }

    public void likedEvent(Event e, List<Event> allEvents) {
        ArrayList<String> aux = new ArrayList<String>();
        for (Event er : allEvents) {
            for (String label : er.getLavels()) {
                aux.add(label);
            }
        }
        Double base = 0.5;
        e.like();
        System.out.println(this.affinity);
        this.eventsLikeIt.add(e.getId());
        System.out.println(this.affinity);
        for (String x : aux) {
            System.out.println(this.affinity);
            if (e.getLavels().contains(x)) {
                System.out.println(this.affinity);
                if (this.affinity.containsKey(x)) {
                    System.out.println("Como le ha gustado la etiqueta" + x + " suben sus puntos");
                    this.affinity.put(x, Math.sqrt(this.affinity.get(x)));
                    System.out.println(this.affinity);
                } else {
                    System.out.println("Como le ha gustado la etiqueta " + x + " suben sus puntos");
                    this.affinity.put(x, Math.sqrt(base));
                    System.out.println(this.affinity);
                }
            } else {
                if (this.affinity == null) {
                    System.out.println("No había afinidad previa");
                    this.affinity = new HashMap<String, Double>();
                    System.out.println(this.affinity);
                }
                System.out.println("Como no le ha gustado la etiqueta " + x + ", bajan sus puntos");
                this.affinity.put(x, base * base);
                System.out.println(this.affinity);
            }
        }
    }

    public void setPassword(String new_password) {
        this.passwordHash = new_password;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public double getValue(Event event) {
        Double aux = 0.0;
        if (this.affinity == null) {
            this.affinity = new HashMap<String, Double>();
        }
        for (String label : event.getLavels()) {
            if (this.affinity.containsKey(label)) {
                aux += this.affinity.get(label);
            } else {
                aux += 0.5;
                this.affinity.put(label, 0.5);
            }
        }
        return aux;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur)).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
