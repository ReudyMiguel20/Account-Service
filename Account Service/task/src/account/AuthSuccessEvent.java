//package account;
//
//import account.entities.Employee;
//import account.service.AuthoritiesServiceImpl;
//import account.service.EmployeeServiceImpl;
//import account.service.LogServiceImpl;
//import account.service.UserServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//
//public class AuthSuccessEvent implements ApplicationListener<AuthenticationSuccessEvent> {
//
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private LogServiceImpl logService;
//    private EmployeeServiceImpl employeeService;
//    private UserServiceImpl userService;
//    private AuthoritiesServiceImpl authoritiesService;
//
//    public AuthSuccessEvent() {
//    }
//
//    @Autowired
//    public AuthSuccessEvent(HttpServletRequest request,
//
//                            LogServiceImpl logService,
//                            EmployeeServiceImpl employeeService,
//                            UserServiceImpl userService,
//                            AuthoritiesServiceImpl authoritiesService
//    ) {
//        this.request = request;
//        this.logService = logService;
//        this.employeeService = employeeService;
//        this.userService = userService;
//        this.authoritiesService = authoritiesService;
//    }
//
//    @Override
//    public void onApplicationEvent(AuthenticationSuccessEvent event) {
//        String personAuth = event.getAuthentication().getName();
//        String endpoint = request.getRequestURI();
//
//        Employee tempEmployee = this.employeeService.getEmployeeByEmail(personAuth);
//
//        if (tempEmployee == null) {
//
//        } else {
//            this.employeeService.resetLoginAttempts(tempEmployee);
//        }
//    }
//}
