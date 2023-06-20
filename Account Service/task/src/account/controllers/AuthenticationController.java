package account.controllers;

import account.entities.Employee;
import account.entities.User;
//import account.exceptions.RestAuthenticationEntryPoint;
import account.exceptions.UserExistException;
import account.service.AuthoritiesServiceImpl;
import account.service.EmployeeServiceImpl;
import account.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private EmployeeServiceImpl employeeService;
    private UserServiceImpl userService;
    private AuthoritiesServiceImpl authoritiesService;
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public AuthenticationController(EmployeeServiceImpl employeeService, UserServiceImpl userService, AuthoritiesServiceImpl authoritiesService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody Employee employee) {
        boolean userExists = this.employeeService.findEmployeeByEmail(employee.getEmail());
        employee.setEmail(employee.getEmail().toLowerCase());

        if (userExists) {
            throw new UserExistException();
//            this.restAuthenticationEntryPoint.commence();
        } else {
            this.employeeService.save(employee);
            this.userService.save(employee);
            this.authoritiesService.save(employee);
            return ResponseEntity.ok().body(employee);
        }
    }
}
