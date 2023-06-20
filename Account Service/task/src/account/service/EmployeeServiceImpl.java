package account.service;

import account.repositories.EmployeeRepository;
import account.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee save(Employee employee) {
        employee.setRole("USER");
//        employee.setEmail(employee.getEmail().toLowerCase());
        return this.employeeRepository.save(employee);
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
    public boolean findEmployeeByEmail(String email) {
        for (Employee x : getAllEmployees()) {
            if (x.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }
}
