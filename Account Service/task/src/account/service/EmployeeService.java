package account.service;

import account.dto.AssignUserRole;
import account.dto.UploadPayroll;
import account.entities.Employee;
import account.entities.EmployeePayroll;
import org.springframework.transaction.annotation.Transactional;
//import account.entities.EmployeePayment;

import java.util.List;

public interface EmployeeService {
    void saveNewEmployee(Employee employee);
    void updateEmployee(Employee employee);
    Employee getEmployeeByEmail(String email);
    void setEmployeePayment(Employee employee, List<UploadPayroll> uploadPayrollList);
    void updatePayrolls(Employee employee, List<UploadPayroll> uploadPayrollList);
    boolean doUserExists(String email);
    EmployeePayroll getSinglePayroll(Employee employee, String period);

//    void updateEmployeeRole(AssignUserRole assignUserRole);
//    void updateEmployeeRole(AssignUserRole assignUserRole);

    boolean samePassword(String username, String newPassword);
    boolean breachedPassword(String newPassword);
    Employee changePassword(String username, String newPassword);

    //Probably need to return employee here
    @Transactional
    void removeOrGrant(AssignUserRole assignUserRole);

    void deleteEmployee(Employee employee);

    //    boolean checkForDuplicateRoles(Employee employee, String role);
    List<Employee> getAllEmployees();
}
