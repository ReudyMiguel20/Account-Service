package account.service;

import account.entities.Employee;

import java.util.List;

public interface EmployeeService {

    void saveNewUser(Employee employee);
    void updateEmployee(Employee employee);
    Employee getEmployeeByEmail(String email);
    boolean userExists(String email);
    boolean samePassword(String username, String newPassword);
    boolean breachedPassword(String newPassword);
    Employee changePassword(String username, String newPassword);
    List<Employee> getAllEmployees();
}
