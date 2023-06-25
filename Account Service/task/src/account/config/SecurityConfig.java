package account.config;


//import account.exceptions.RestAuthenticationEntryPoint;

//import account.exceptions.CustomAccessDeniedHandler;
//import account.exceptions.CustomAuthenticationFailureHandler;

import account.exceptions.RestAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;


@Configuration
@EnableWebSecurity
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
//                    try {
                    auth.requestMatchers("/h2-console/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/auth/signup", "/actuator/shutdown").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/auth/changepass").hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR");
                    auth.requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("USER", "ACCOUNTANT");
                    auth.requestMatchers(HttpMethod.POST, "/api/acct/payments").hasRole("ACCOUNTANT");
                    auth.requestMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole("ACCOUNTANT");
                    auth.requestMatchers(HttpMethod.GET, "/api/admin/user/").hasAnyRole("ADMINISTRATOR");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/admin/user/**").hasAnyRole("ADMINISTRATOR");
                    auth.requestMatchers(HttpMethod.PUT, "/api/admin/user/role/**").hasAnyRole("ADMINISTRATOR");
                    auth.requestMatchers(HttpMethod.PUT, "/api/admin/user/role").hasAnyRole("ADMINISTRATOR");
                    try {
                        auth.anyRequest().permitAll()
                                .and()
                                .exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


//                                .and()
//                                .exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
                });

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            //customize the response to get your desired response. (check out getOutputStream() and getWriter() methods)
            //get info about the request from request variable
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            //Removing 'uri=' from the path
            String path = request.getRequestURI();
            path = path.replace("uri=", "");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = new LinkedHashMap<>();

            responseBody.put("timestamp", Calendar.getInstance().getTime());
            responseBody.put("status", HttpStatus.FORBIDDEN.value());
            responseBody.put("error", "Forbidden");
            responseBody.put("message", "Access Denied!");
            responseBody.put("path", path);

            response.getOutputStream()
                    .println(objectMapper.writeValueAsString(responseBody));
        };
    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler(){
//        return new CustomAccessDeniedHandler();
//    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }

}





