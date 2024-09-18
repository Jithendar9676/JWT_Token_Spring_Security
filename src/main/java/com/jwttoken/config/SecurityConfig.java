package com.jwttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.jwttoken.service.UserInfoDetailedSerivce;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	//InMemoryAuthentication
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		UserDetails admin=User.withUsername("ADMIN").password(passwordEncoder.encode("ADMIN")).roles("ADMIN").build();
//		UserDetails user=User.withUsername("USER").password(passwordEncoder.encode("USER")).roles("USER").build();
//		
//		return new InMemoryUserDetailsManager(admin,user);
//		
//	}
	
	//dbAuthentication
	@Bean
	public UserDetailsService detailsService() {
		return new UserInfoDetailedSerivce();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
		https.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
//		.authorizeHttpRequests().requestMatchers("/Product/welcome","/Product/addProduct","user/saveUserInfo","Jwt/generateToken").permitAll()
//				.requestMatchers("/Product/**").authenticated()
//				.anyRequest().authenticated()
//				.and().formLogin();
		.authorizeHttpRequests(auth->auth.requestMatchers("/Product/welcome","/Product/addProduct","user/saveUserInfo","Jwt/generateToken",
				"/api/v1/auth/**",
				"/v2/api-docs",
				"/v3/api-docs",
				"/v3/api-docs/**",
				"/swagger-resources",
				"/swagger-resources/**",
				"/confinguration/ui",
				"/configuration/security",
				"/swagger-ui/**",
				"/webjars/**",
				"/swagger-ui.html"

				).permitAll());
				//.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return https.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(detailsService());
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
