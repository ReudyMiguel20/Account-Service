package account.controllers;

import account.BreachedPasswords;
import account.dto.PasswordChange;
import account.dto.SuccessfulPassword;
import account.entities.Employee;
//import account.exceptions.RestAuthenticationEntryPoint;
import account.exceptions.BreachedPassword;
import account.exceptions.SamePassword;
import account.exceptions.UserExistException;
import account.service.AuthoritiesServiceImpl;
import account.service.EmployeeServiceImpl;
import account.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthenticationController {

    private EmployeeServiceImpl employeeService;
    private UserServiceImpl userService;
    private AuthoritiesServiceImpl authoritiesService;
    private BreachedPasswords breachedPasswords;

    @Autowired
    public AuthenticationController(EmployeeServiceImpl employeeService, UserServiceImpl userService, AuthoritiesServiceImpl authoritiesService, BreachedPasswords breachedPasswords) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
        this.breachedPasswords = breachedPasswords;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUser(@Valid @RequestBody Employee employee) {
        boolean userExists = this.employeeService.userExists(employee.getEmail());
        employee.setEmail(employee.getEmail().toLowerCase());

        if (userExists) {
            throw new UserExistException();
        } else {
            this.employeeService.saveNewUser(employee);
            this.userService.saveNewUser(employee);
            this.authoritiesService.save(employee);
            return ResponseEntity.ok().body(employee);
        }
    }

    @PostMapping("/changepass")
    public ResponseEntity<?> changePassword(Authentication auth, @Valid @RequestBody PasswordChange newPassword) {
        System.out.println("Aintnoway");
        String username = auth.getName();
        boolean breachedPassword = this.employeeService.breachedPassword(newPassword.getNewPassword());
        boolean samePassword = this.employeeService.samePassword(username, newPassword.getNewPassword());


        if (breachedPassword) {
            throw new BreachedPassword();
        } else if (samePassword){
            throw new SamePassword();
        } else {
            Employee tempEmployee = this.employeeService.changePassword(username, newPassword.getNewPassword());
            SuccessfulPassword successfulPassword = this.userService.changePassword(tempEmployee);
            return ResponseEntity.ok().body(successfulPassword);
        }

    }
}
