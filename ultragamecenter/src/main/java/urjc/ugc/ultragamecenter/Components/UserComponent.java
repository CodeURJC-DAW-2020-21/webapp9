package urjc.ugc.ultragamecenter.Components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.User;

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
		ArrayList<Event> aux = new ArrayList<>();
		ArrayList<Event> aux2 = new ArrayList<>();
		if (user == null) {
			for(Integer i=0;i<Math.min(events.size(), c);i++){
				aux2.add(events.get(i));
			}
			return aux2;
		}
		while (!events.isEmpty()) {
			int index = 0;
			for (int x = 0; x < events.size(); x++) {
				if (user.getValue(events.get(index)) < user.getValue(events.get(x))) {
					index = x;
				}
			}
			aux.add(events.get(index));
			events.remove(index);
		}
		for(Integer i=0;i<(Math.min(aux.size(), c));i++){
			aux2.add(aux.get(i));
		}
		return aux2;
	}

}
