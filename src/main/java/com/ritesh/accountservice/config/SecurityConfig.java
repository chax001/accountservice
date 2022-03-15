package com.ritesh.accountservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author rites
 * 
 *         this is security config class to configure User authentication and
 *         authrization
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * this method creates inMemoryAuthentication for user and admin
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user")).roles("USER");
	}

	// security based on ROLE
	/**
	 *
	 * this mehtod validates user and admin and also configure http session
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/user/**")
				.hasAnyAuthority("ROLE_USER").anyRequest().authenticated().and().formLogin().permitAll().and().logout()
				.permitAll().and().exceptionHandling().accessDeniedPage("/401");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
		http.sessionManagement().sessionFixation().migrateSession();

	}

}
