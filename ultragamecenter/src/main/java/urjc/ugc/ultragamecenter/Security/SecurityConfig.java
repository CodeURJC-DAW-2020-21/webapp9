package urjc.ugc.ultragamecenter.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Public pages

		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/loginerror").permitAll()
			.antMatchers("/logout").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/newUser").permitAll()
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.antMatchers("/user/*").hasAnyRole("USER")
			.antMatchers("/like*").hasAnyRole("USER")
			.antMatchers("/profile*").hasAnyRole("USER")
		.and()
			// Login pages
			.formLogin()
				.loginPage("/login") 
				// Login parameter names
				.usernameParameter("email")
				.passwordParameter("password")
				// Redirect Url on success and on failure
				.defaultSuccessUrl("/profile")
				.failureUrl("/login")
			.and()
				// Logout
				.logout().logoutUrl("/loggout").logoutSuccessUrl("/");

		// Disable CSRF Protection, not compatible with current version of Mustache
		http.csrf().disable();
	}
}
