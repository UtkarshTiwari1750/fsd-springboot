// ...existing code...
package payment_gateway.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", 
                    "/index.html",
                    "/index",
                    "/favicon.ico",
                    "/assets/**",
                    "/static/**",
                    "/**/*.js",
                    "/**/*.css",
                    "/**/*.map",
                    "/**/*.png",
                    "/**/*.jpg",
                    "/**/*.jpeg",
                    "/**/*.svg",
                    "/**/main-*.js",
                    "/**/polyfills-*.js",
                    "/**/styles-*.css",
                    "/debug/**",
                    "/**/debug/**",
                    "/api/public/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}