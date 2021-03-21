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

@Component
@SessionScope
public class UserComponent {

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
		return isLoggedUser() && this.user.getRoles().equals(RoleType.ADMINISTRATOR);
	}

	public RoleType getRole() {
		return this.user.getRoles();
	}

	public void logOut() {
		this.user = null;
	}

	public boolean hasLiked(Long event) {
		return this.user.getEventsLiked().contains(event);
	}

	public void like(Event event) {
		this.user.likedEvent(event);
	}



	public Page<Event> sort(Page<Event> PageEvents) {
		List<Event> events = PageEvents.getContent();
		if (user == null) {
			return new PageImpl<Event>(events);
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
		return new PageImpl<Event>(aux);
	}

}