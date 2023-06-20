package account.service;

import account.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee save(Employee employee);
    Employee getEmployeeByEmail(String email);
    boolean findEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
}
