package payment_gateway.demo; // use your package here

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // enable CORS, uses your CorsConfig bean
            .and()
            .csrf().disable() // disable CSRF for REST APIs, especially with Angular
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/assets/**",   // all files inside assets
                    "/static/**",   // all static resources
                    "/**/*.js",     // JS files
                    "/**/*.css",    // CSS files
                    "/api/public/**" // any public APIs you want open
                ).permitAll()
                .anyRequest().authenticated() // secure everything else
            )
            .httpBasic(); // basic auth, or replace with JWT/oauth as needed

        return http.build();
    }
}
