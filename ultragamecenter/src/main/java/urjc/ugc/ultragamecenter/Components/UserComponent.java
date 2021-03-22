package urjc.ugc.ultragamecenter.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.repositories.EventRepository;
import urjc.ugc.ultragamecenter.repositories.UserRepository;
import urjc.ugc.ultragamecenter.types.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.types.RoleType;

@Component
@SessionScope
public class UserComponent {
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	@Autowired
	private EventRepository erepository;

	private User user;

	public User getLoggedUser() {
		return this.user;
	}

	public void setLoggedUser(User user) {
		this.user = user;
	}

	public boolean isLoggedUser() {
		return this.user != null;
	}

	public boolean isAdmin() {
		return isLoggedUser() && this.user.getRoles().contains("ADMIN");
	}

	public String getRole() {
		if (this.user.getRoles().contains("ADMIN")) {
			return "ADMIN";
		}
		return "USER";
	}

	public void logOut() {
		this.user = null;
	}

	public boolean hasLiked(Long event) {
		
		return this.user.hasLiked(event);
	}

	public void like(Event event,List<Event> allEvents) {
		this.user.likedEvent(event,allEvents);
	}

	public List<Event> sort(List<Event> events,Integer c) {
		ArrayList<Event> aux = new ArrayList<Event>();
		ArrayList<Event> aux2 = new ArrayList<Event>();
		if (user == null) {
			for(Integer i=0;i<c;i++){
				aux2.add(events.get(i));
			}
			return aux2;
		}
		while (events.size() != 0) {
			int index = 0;
			for (int x = 0; x < events.size(); x++) {
				if (user.getValue(events.get(index)) < user.getValue(events.get(x))) {
					index = x;
				}
			}
			aux.add(events.get(index));
			events.remove(index);
		}
		for(Integer i=0;i<c;i++){
			aux2.add(aux.get(i));
		}
		return aux2;
	}

}