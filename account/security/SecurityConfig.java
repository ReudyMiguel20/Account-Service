package account.security;


//import account.exceptions.RestAuthenticationEntryPoint;

import account.exceptions.RestAuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
public class SecurityConfig {


    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf(csrf -> {
                    csrf.disable();
                    csrf.ignoringRequestMatchers("/h2-console/**");
                })
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/h2-console/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/auth/signup", "/actuator/shutdown").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/api/empl/payment").hasRole("USER");
                    auth.anyRequest().authenticated();
                });

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        return http.build();
    }

}





