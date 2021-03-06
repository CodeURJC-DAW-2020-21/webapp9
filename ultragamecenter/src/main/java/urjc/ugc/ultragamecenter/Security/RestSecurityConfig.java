package urjc.ugc.ultragamecenter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import urjc.ugc.ultragamecenter.security.jwt.JwtRequestFilter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}

	

	// Expose AuthenticationManager as a Bean to be used in other services
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");

		// URLs that need authentication to access to it

		// Events
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/event/like/**").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/event/like/**").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/event/").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/event/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/event/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/event/DATA/**").hasRole("ADMIN");

		// User
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/user/**").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/image").authenticated();
		// Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf().disable();

		// Disable Http Basic Authentication
		http.httpBasic().disable();

		// Disable Form login Authentication
		http.formLogin().disable();

		// Avoid creating session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http.cors();
	}

}
