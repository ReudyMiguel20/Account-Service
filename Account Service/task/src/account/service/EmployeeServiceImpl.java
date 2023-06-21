package account.service;

import account.BreachedPasswords;
import account.exceptions.BreachedPassword;
import account.repositories.EmployeeRepository;
import account.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder encoder;
    private BreachedPasswords breachedPasswords;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder encoder, BreachedPasswords breachedPasswords) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.breachedPasswords = breachedPasswords;
    }

    /* This method adds a new Employee to the database, before storing the employee it checks if the password is
    *  breached, and then encode the password using Bcrypt with a strength of 13 */
    @Override
    public void saveNewUser(Employee employee) {
        employee.setRole("USER");
        breachedPassword(employee.getPassword());
        employee.setPassword(encoder.encode(employee.getPassword()));

        this.employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        for (Employee x : getAllEmployees()) {
            if (x.getEmail().equalsIgnoreCase(email)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public boolean userExists(String email) {
        for (Employee x : getAllEmployees()) {
            if (x.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean samePassword(String email, String newPassword) {
        for (Employee x : getAllEmployees()) {
            if (x.getEmail().equals(email)){
                return encoder.matches(newPassword, x.getPassword());
            }
        }
        return false;
    }

    @Override
    public boolean breachedPassword(String newPassword) {
        for (String x : this.breachedPasswords.listOfBreachedPasswords()) {
            if (x.equals(newPassword)){
                throw new BreachedPassword();
            }
        }
        return false;
    }

    @Override
    public Employee changePassword(String username, String newPassword) {
        Employee tempEmployee = getEmployeeByEmail(username);
        tempEmployee.setPassword(this.encoder.encode(newPassword));
        updateEmployee(tempEmployee);

        return tempEmployee;
        }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }
}
