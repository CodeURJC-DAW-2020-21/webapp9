package urjc.ugc.ultragamecenter.Components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

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

}