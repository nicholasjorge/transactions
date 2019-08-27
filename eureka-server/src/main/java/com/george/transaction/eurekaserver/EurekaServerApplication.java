package com.george.transaction.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().ignoringAntMatchers("/eureka/**");
            super.configure(http);
            //            http
            //                    .csrf()
            //                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

            //            http.cors().and()
        }

        //        @Bean
        //        CorsConfigurationSource corsConfigurationSource() {
        //            CorsConfiguration configuration = new CorsConfiguration();
        //            configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        //            configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        //            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //            source.registerCorsConfiguration("/**", configuration);
        //            return source;
        //        }
    }

}
