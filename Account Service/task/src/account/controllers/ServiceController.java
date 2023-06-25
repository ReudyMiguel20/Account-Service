package account.controllers;

import account.BreachedPasswords;
import account.dto.AssignUserRole;
import account.dto.MessageStatus;
import account.entities.Authorities;
import account.entities.Employee;
import account.exceptions.AccessDenied;
import account.exceptions.BadRequest;
import account.exceptions.UserNotFound;
import account.service.AuthoritiesServiceImpl;
import account.service.EmployeeServiceImpl;
import account.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class ServiceController {

    private EmployeeServiceImpl employeeService;
    private AuthoritiesServiceImpl authoritiesService;

    @Autowired
    public ServiceController(EmployeeServiceImpl employeeService, AuthoritiesServiceImpl authoritiesService) {
        this.employeeService = employeeService;
        this.authoritiesService = authoritiesService;
    }

    @PutMapping("/user/role")
    public ResponseEntity<?> setUserRole(Authentication auth, @RequestBody AssignUserRole assignUserRole) {


        Employee tempEmployee = this.employeeService.getEmployeeByEmail(assignUserRole.getUsername());

        if (tempEmployee == null) {
            throw new UserNotFound();
        } else {
            this.employeeService.removeOrGrant(assignUserRole);
            return ResponseEntity.ok().body(tempEmployee);
        }
    }


    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteUser(Authentication auth, @PathVariable String username) {
        System.out.println(auth.getAuthorities());
        Employee tempEmployee = this.employeeService.getEmployeeByEmail(username);

        if (tempEmployee == null) {
            //throw exception
            throw new UserNotFound();
        } else {
            this.employeeService.deleteEmployee(tempEmployee);
            MessageStatus messageStatus = new MessageStatus(username, "Deleted successfully!");
            return ResponseEntity.ok().body(messageStatus);
        }
    }


    @GetMapping("/user/")
    public ResponseEntity<?> getAllUsers() {
        if (this.authoritiesService.howManyUsersWithAuthorities() == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(this.employeeService.getAllEmployees());
        }
    }

}
