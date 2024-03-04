# spring-boot-oauth2-client
Implementing client app with different authorization servers .

## 1. implement a secure oauth 2 client app with github as authorization server
In this section will configure a feature provided by *spring-boot-starter-oauth2-client* a login feature provided by oauth2 client
``` java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	return http.authorizeHttpRequests(auth -> {
                                              auth.requestMatchers("/").permitAll();
		                                      auth.requestMatchers("/favicon.ico").permitAll();
			                                  auth.anyRequest().authenticated();
		                                    }
                                    )
			.oauth2Login(withDefaults())
			.build();
}
```


