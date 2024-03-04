# spring-boot-oauth2-client
Implementing client app with different authorization servers .

## 1. implement a secure oauth 2 client app with github as authorization server and with oauth2Login form
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


## 2.Implement a secure outh 2 client app with keycloak as authorization server  using jwt token for authorization
### 2.1 At the authorization server side
1. start Keycloack server , we can  run the server in a  container 
with 
```docker
docker run -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.7 start-dev
```
2. Create a realm for our springboot application : lets called it SpringBootKeyCloak

3. Create a client and we called login-app which also its id , the only thing we reconfigure is valid redirect uri For our example a Spring Boot Application running at port 8080 that’ll use this client.Hence http://localhost:8080/*

4. Create a role and a user (Keycloak uses Role-Based Access; therefore, each user must have a role.)

   4.1. Create a role in roles realm called a user

   4.2.  Create a user named user1 with password  password and assigned the role we created in 4.1 to it 


5. set clientscope so that Keycloak passes all the roles for the authenticating user to the token  ie set microprofile-jwt to “default” 

6. to generate the access-token and refresh token : do a post request 

```sh
curl --location 'http://localhost:8081/realms/SpringBootKeycloak/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=EE37EFB9E48FFC92C6FC37B76725F6F6' \
--data-urlencode 'client_id=login-app' \
--data-urlencode 'username=user1' \
--data-urlencode 'password=password' \
--data-urlencode 'grant_type=password'
```
### 2.2 At the client side
Configure your clien id ,authorization grant type,scope ,uri of the keycloak server, username and ressource jwt ressource issuer at  *check application.properties*.

Configure your filter to allow only access to "\" and make all the others requests available only authorization via jwt token 
```java
@Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.csrf(AbstractHttpConfigurer::disable);
	http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	http.authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll()
	                                        .anyRequest().authenticated());
	http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	return  http.build();
}
```
