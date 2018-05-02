package no.cmarker.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Christian Marker on 16/04/2018 at 14:50.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception{
		return super.userDetailsServiceBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) {
		try {
			http.csrf().disable();
			http.authorizeRequests()
					.antMatchers("/", "/index.xhtml", "/signup.xhtml", "/assets/**").permitAll()
					.antMatchers("/javax.faces.resource/**").permitAll()
					.antMatchers( "/pagetwo.xhtml").authenticated()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login.xhtml")
					.permitAll()
					.failureUrl("/login.jsf?error=true")
					.defaultSuccessUrl("/index.xhtml")
					.and()
					.logout()
					.logoutSuccessUrl("/index.xhtml");
					
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		
		try {
			auth.jdbcAuthentication()
					.dataSource(dataSource)
					.usersByUsernameQuery(
							"SELECT username, password, enabled " +
									"FROM users " +
									"WHERE username = ?"
					)
					.authoritiesByUsernameQuery(
							"SELECT x.username, y.roles " +
									"FROM users x, user_roles y " +
									"WHERE x.username = ? and y.user_username = x.username "
					)
					/*
						Note: in BCrypt, the "password" field also contains the salt
					 */
					.passwordEncoder(passwordEncoder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
