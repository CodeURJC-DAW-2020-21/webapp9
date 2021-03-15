package urjc.ugc.ultragamecenter.Components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.User;
import urjc.ugc.ultragamecenter.Types.RoleType;

@Component
@SessionScope
public class UserComponent {

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

	public boolean isAdmin(){
		return isLoggedUser() && this.user.getRoles().equals(RoleType.ADMINISTRATOR);
	}

    public RoleType getRole() {
        return this.user.getRoles();
    }

    public void logOut() {
		this.user=null;
    }

    public boolean hasLiked(Event event) {
        return this.user.getEvents().contains(event);
    }

    public void like(Event event) {
		this.user.likedEvent(event);
    }

}