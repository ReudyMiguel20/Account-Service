package account.service;

import account.dto.UploadPayroll;
import account.entities.Employee;
import account.entities.EmployeePayroll;
//import account.entities.EmployeePayment;

import java.util.List;

public interface EmployeeService {
    void saveNewUser(Employee employee);
    void updateEmployee(Employee employee);
    Employee getEmployeeByEmail(String email);
    void setEmployeePayment(Employee employee, List<UploadPayroll> uploadPayrollList);
    void updatePayrolls(Employee employee, List<UploadPayroll> uploadPayrollList);
    boolean userExists(String email);
    EmployeePayroll getSinglePayroll(Employee employee, String period);
    boolean samePassword(String username, String newPassword);
    boolean breachedPassword(String newPassword);
    Employee changePassword(String username, String newPassword);
    List<Employee> getAllEmployees();
}
