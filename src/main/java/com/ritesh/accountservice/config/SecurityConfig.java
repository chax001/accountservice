package com.ritesh.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

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

	/**
	 * this method creates inMemoryAuthentication for user and admin
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
	}

	// security based on ROLE
	/**
	 *
	 * this mehtod validates user and admin and also configure http session
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/user/**")
				.hasAnyAuthority("ROLE_USER").anyRequest().authenticated().and().formLogin().permitAll().and().logout()
				.permitAll().and().exceptionHandling().accessDeniedPage("/403");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
		http.sessionManagement().sessionFixation().none();

	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

}
