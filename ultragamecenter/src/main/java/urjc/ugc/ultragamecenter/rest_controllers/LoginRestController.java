package urjc.ugc.ultragamecenter.rest_controllers;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.security.jwt.AuthResponse;
import urjc.ugc.ultragamecenter.security.jwt.LoginRequest;
import urjc.ugc.ultragamecenter.security.jwt.UserLoginService;
import urjc.ugc.ultragamecenter.security.jwt.AuthResponse.Status;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

	@Autowired
	private UserLoginService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@CookieValue(name = "accessToken", required = false) String accessToken,
			@CookieValue(name = "refreshToken", required = false) String refreshToken,
			@RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest, accessToken, refreshToken);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(
			@CookieValue(name = "refreshToken", required = false) String refreshToken) {

		return userService.refresh(refreshToken);
	}

	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {

		return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(request, response)));
	}
}