package urjc.ugc.ultragamecenter.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import urjc.ugc.ultragamecenter.Types.RoleType;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5767343013628002370L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String email;
    private ArrayList<Long> eventsLikeIt;
    private ArrayList<Tablegame> reservatedTables;
    private HashMap<String, Double> affinity;
    private ArrayList<Event> recomendated;
    
    @ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
    private Set<RoleType> role;

    public UserEntity() {
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
    

    public UserEntity(String name, String lastname, String password, String email) {
        this.name = name;
        this.lastName = lastname;
        this.passwordHash = password;
        this.email = email;
        this.eventsLikeIt = new ArrayList<Long>();
        this.reservatedTables = new ArrayList<Tablegame>();
        this.role = new TreeSet<RoleType>();
        this.role.add(RoleType.USER);
    }
 

    public UserEntity(String name, String password, String email, RoleType role) {
        this.name = name;
        this.passwordHash = password;
        this.lastName = "";
        this.email = email;
        this.eventsLikeIt = new ArrayList<Long>();
        this.reservatedTables = new ArrayList<Tablegame>();
        this.role = new TreeSet<RoleType>();
        this.role.add(role);
    }
    

    public Long getId() {
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

    public ArrayList<Long> getEvents() {
        return this.eventsLikeIt;
    }

    public void addEvent(Event event) {
        this.eventsLikeIt.add(event.getId());
    }

    public ArrayList<Tablegame> getTables() {
        return this.reservatedTables;
    }

    public RoleType getRoles() {
    	if (role.contains(RoleType.ADMIN)) {
    		return RoleType.ADMIN;
    	}
    	return RoleType.USER;
    }

    public void setRoles(RoleType role) {
        this.role.add(role);
    }
    
    public void giveRoles(RoleType role) {
    	if (this.role.contains(role)) {
    		return;
    	} else {
    		if (role == RoleType.ADMIN) {
    			this.role.remove(RoleType.ADMIN);
    			this.role.add(RoleType.USER);
    		} else {
    			this.role.remove(RoleType.USER);
    			this.role.add(RoleType.ADMIN);
    		}
    	}
    }
    
    public void addTable(Tablegame table) {
        this.reservatedTables.add(table);
    }

    @Override
    public String toString() {
        return "User [id=" + this.id + ", Name=" + this.name + ", lastName=" + this.lastName + ", email=" + this.email
                + ", envets liked=" + this.eventsLikeIt + ", password=" + this.passwordHash + ", Tables="
                + this.reservatedTables + ", role=" + this.role + "]";
    }

    public void likedEvent(Event e) {
        Double base = 0.5;
        e.like();
        this.eventsLikeIt.add(e.getId());
        for (String x : Event.allLabels) {
            if (e.getLavels().contains(x)) {
                if (this.affinity.containsKey(x)) {
                    this.affinity.put(x, Math.sqrt(this.affinity.get(x)));
                } else {
                    this.affinity.put(x, Math.sqrt(base));
                }
            } else {
                if (this.affinity == null) {
                    this.affinity = new HashMap<String,Double>();
                }
                this.affinity.put(x, base * base);
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
        Double aux=0.0;
        if (this.affinity == null) {
            this.affinity = new HashMap<String,Double>();
        }
        for(String label:event.getLavels()){
            if(this.affinity.containsKey(label)){
                aux+=this.affinity.get(label);
            } else{
                aux+=0.5;
                this.affinity.put(label, 0.5);
            }
        }
        return aux;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.name())).collect(Collectors.toList());
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
