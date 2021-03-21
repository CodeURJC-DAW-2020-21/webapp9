package urjc.ugc.ultragamecenter.Components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.User;
import urjc.ugc.ultragamecenter.Types.RoleType;

@Component
@SessionScope
public class UserComponent {
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

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
		return isLoggedUser() && this.user.getRoles().equals(RoleType.ADMIN);
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
		return this.user.getEvents().contains(event);
	}

	public void like(Event event) {
		this.user.likedEvent(event);
	}

	public List<Event> sort(List<Event> events) {
		if (user == null) {
			return events;
		}
		ArrayList<Event> aux = new ArrayList<Event>();
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
		return aux;
	}

}