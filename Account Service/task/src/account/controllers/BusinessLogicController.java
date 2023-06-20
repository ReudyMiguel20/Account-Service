package account.controllers;

import account.entities.Employee;
import account.entities.User;
import account.exceptions.UnauthorizedUser;
import account.exceptions.UserExistException;
import account.service.AuthoritiesServiceImpl;
import account.service.EmployeeServiceImpl;
import account.service.UserServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessLogicController {

    private EmployeeServiceImpl employeeService;
    private UserServiceImpl userService;
    private AuthoritiesServiceImpl authoritiesService;

    @Autowired
    public BusinessLogicController(EmployeeServiceImpl employeeService, UserServiceImpl userService, AuthoritiesServiceImpl authoritiesService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<?> testAuth(Authentication auth) {
        String user = auth.getName().toLowerCase();
        Employee testEmployee = this.employeeService.getEmployeeByEmail(user);

        if (testEmployee == null) {
            return ResponseEntity.badRequest().body("hey");
        } else {
            return ResponseEntity.ok().body(testEmployee);
        }
    }

}
