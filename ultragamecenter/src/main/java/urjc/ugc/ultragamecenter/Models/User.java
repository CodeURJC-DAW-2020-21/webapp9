package urjc.ugc.ultragamecenter.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import urjc.ugc.ultragamecenter.requests.UserDTO;

@Entity
@EntityListeners(AuditingEntityListener.class)

@Data
@NoArgsConstructor
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

    @JsonIgnore
    private ArrayList<Long> eventsLikeIt;
    
    private ArrayList<String> referencedCodes;

    private HashMap<String, Double> affinity;

    private ArrayList<Event> recomendated;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User() {
    }

    public User(UserDTO u){
        super();
        this.eventsLikeIt = new ArrayList<>();
        this.referencedCodes = new ArrayList<>();
        this.affinity = new HashMap<>();
        this.recomendated = new ArrayList<>();
        this.name = u.getName();
        this.profileSrc = "uploadImages/userImg/defaultuser.png";
        this.passwordHash = new BCryptPasswordEncoder().encode(u.getPasswordHash());
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.roles = new ArrayList<>();
        this.roles.add("USER");
    }

    public User(String name, String lastName, String password, String email) {
        super();
        this.eventsLikeIt = new ArrayList<>();
        this.referencedCodes = new ArrayList<>();
        this.affinity = new HashMap<>();
        this.recomendated = new ArrayList<>();
        this.name = name;
        this.profileSrc = "uploadImages/userImg/defaultuser.png";
        this.passwordHash = new BCryptPasswordEncoder().encode(password);
        this.lastName = lastName;
        this.email = email;
        this.roles = new ArrayList<>();
        this.roles.add("USER");
    }

    public boolean matchPasword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, this.passwordHash);
    }

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

    @JsonIgnore
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


    @JsonIgnore
    public String getPassword() {
        return this.passwordHash;
    }

    @JsonIgnore
    public List<Long> getEventsLiked() {
        return this.eventsLikeIt;
    }

    @JsonIgnore
    public void setEventLiked(Event event) {
        this.eventsLikeIt.add(event.getId());
    }

    @JsonIgnore
    public List<String> getReferencedCodes() {
        return this.referencedCodes;
    }

    
    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(String role) {
        this.roles.add(role);
    }

    public void setReferencedCode(List<String> rc) {
        this.referencedCodes = (ArrayList<String>) rc;
    }

    @JsonIgnore
    public String getProfileSrc() {
        return profileSrc;
    }

    public void setProfileSrc(String src) {
        this.profileSrc = src;
    }

    public void giveRoles(String role) {
        if (!this.roles.contains(role)) {

            if (role.equals("ADMIN")) {
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
        ArrayList<String> aux = new ArrayList<>();
        for (Event er : allEvents) {
            for (String label : er.getLavels()) {
                aux.add(label);
            }
        }
        Double base = 0.5;
        e.like();
        if (this.affinity == null) {
            this.affinity = new HashMap<>();
        }
        this.eventsLikeIt.add(e.getId());
        for (String x : aux) {
            if (e.getLavels().contains(x)) {
                if (this.affinity.containsKey(x)) {
                    this.affinity.put(x, Math.sqrt(this.affinity.get(x)));
                } else {
                    this.affinity.put(x, Math.sqrt(base));
                }
            } else {
                this.affinity.put(x, base * base);
            }
        }
    }

    public void setPassword(String newPassword) {
        this.passwordHash= new BCryptPasswordEncoder().encode(newPassword);
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    @JsonIgnore
    public double getValue(Event event) {
        Double aux = 0.0;
        if (this.affinity == null) {
            this.affinity = new HashMap<>();
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

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur)).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.email;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasLiked(Long event) {
        return this.eventsLikeIt != null && this.eventsLikeIt.contains(event);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id2) {
        this.id=id2;
    }

    @JsonIgnore
    public boolean isAdmin(){
        return this.roles.contains("ADMIN");
    }

    @JsonIgnore
    public List<Event> sort(List<Event> events, Integer c){
        ArrayList<Event> aux = new ArrayList<>();
        ArrayList<Event> aux2 = new ArrayList<>();
        while(!events.isEmpty()){
            int index = 0;
            for(int x = 0; x < events.size();x++){
                if(getValue(events.get(index))< getValue(events.get(x))){
                    index = x;
                }
            }
            aux.add(events.get(index));
            events.remove(index);
        }
        for(int i = 0 ;i<Math.min(aux.size(),c);i++){
            aux2.add(aux.get(i));
        }
        return aux2;
    }

    public void edit(UserDTO editedUser) {
        this.email=editedUser.getEmail();
        this.name =editedUser.getLastName();
        this.passwordHash=new BCryptPasswordEncoder().encode(editedUser.getPasswordHash());
        this.lastName = editedUser.getLastName();
    }

}
