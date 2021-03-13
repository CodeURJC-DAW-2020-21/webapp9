package urjc.ugc.ultragamecenter.Components;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import urjc.ugc.ultragamecenter.Models.User;

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
		for(int i=0; i<=this.user.getRoles().size();i++){
			if (this.user.getRoles().get(i)=="ROLE_ADMIN"){
				return true;
			}
		}
		return false;
	}

}