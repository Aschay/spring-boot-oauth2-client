package com.aschay.keycloakoauth2client.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;



@Configuration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
class WebSecurityConfig {

	    @Bean
	    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	        return new NullAuthenticatedSessionStrategy();
	    }

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	http.csrf(AbstractHttpConfigurer::disable);
			http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    	http.authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll()
			                                       .anyRequest().authenticated());
	    	http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	 	   return  http.build();
		}
	    
}