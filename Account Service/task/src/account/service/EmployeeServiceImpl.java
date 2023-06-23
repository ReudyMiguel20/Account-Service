package account.service;

import account.BreachedPasswords;
import account.dto.UploadPayroll;
import account.entities.EmployeePayroll;
import account.exceptions.BadRequest;
import account.exceptions.BreachedPassword;
import account.exceptions.ControllerExceptionHandler;
import account.repositories.EmployeeRepository;
import account.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder encoder;
    private BreachedPasswords breachedPasswords;
    private EmployeePayrollServiceImpl employeePayrollService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder encoder, BreachedPasswords breachedPasswords, EmployeePayrollServiceImpl employeePayrollService) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.breachedPasswords = breachedPasswords;
        this.employeePayrollService = employeePayrollService;
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

    /* Method that receives an employee and a list of UploadPayroll objects to convert it to EmployeePayroll objects
     * which are used to save this info on the database and adds the values to the current employee in their List<EmployeePayroll> list.
     *  Then it proceeds to update the employee with this new info to persist it on the database.
     *
     * If the employee is different from the original that was passed on the method then it changes employee based
     * on their values, a user must be registered on the database, otherwise an exception will be thrown
     *
     * An employee payroll period should be unique, this means that it cannot be paid twice or more on the same period. */
    @Override
    @Transactional
    public void setEmployeePayment(Employee employee, List<UploadPayroll> uploadPayrollList) {
//        List<EmployeePayroll> employeePayrollList = new ArrayList<>();

        //Convert the data from UploadPayroll Object to EmployeePayroll
        for (UploadPayroll x : uploadPayrollList) {
            Employee tempEmployee = getEmployeeByEmail(x.getEmail());

            if (tempEmployee == null) {
                throw new BadRequest();
            } else if (!tempEmployee.equals(employee)) {
                employee = tempEmployee;
            }

            EmployeePayroll employeePayrollInfo = new EmployeePayroll(tempEmployee);
            employeePayrollInfo.setPeriod(x.getPeriod());
            employeePayrollInfo.setSalary(x.getSalary());

            //This is to check if there's repeated periods
            for (EmployeePayroll y : tempEmployee.getEmployeePaymentList()) {
                if (y.getPeriod().equalsIgnoreCase(employeePayrollInfo.getPeriod())) {
                    throw new BadRequest();
                }
            }

            //Saving the employeePayrollInfo to the table on the database and adding the payroll to the respective employee
            this.employeePayrollService.save(employeePayrollInfo);
            tempEmployee.addEmployeePayroll(employeePayrollInfo);
            updateEmployee(tempEmployee);
        }
    }

    /* */

    @Override
    @Transactional
    public void updatePayrolls(Employee employee, List<UploadPayroll> uploadPayrollList) {
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();

        //Convert the data from UploadPayroll Object to EmployeePayroll
        for (UploadPayroll x : uploadPayrollList) {
            if (employee.getEmail().equalsIgnoreCase(x.getEmail())) {
                EmployeePayroll employeePayrollInfo = new EmployeePayroll(employee);
                employeePayrollInfo.setPeriod(x.getPeriod());
                employeePayrollInfo.setSalary(x.getSalary());
                employeePayrollList.add(employeePayrollInfo);
            }
        }

        //Need to throw an error here in case one of the dates are wrong
        for (EmployeePayroll newPayrollInfo : employeePayrollList) {
            for (EmployeePayroll oldPayrollInfo : employee.getEmployeePaymentList()) {
                if (newPayrollInfo.getPeriod().equals(oldPayrollInfo.getPeriod())) {
                    oldPayrollInfo.setSalary(newPayrollInfo.getSalary());
                }
            }
        }

        updateEmployee(employee);
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
    public EmployeePayroll getSinglePayroll(Employee employee, String period) {
        /* Formatting the period from "numberOfMonth-yyyy" to "nameOfMonth-yyyy" and checking if it matches the regex
         *  if it doesn't a new BadRequest exception is going to be thrown */
        Pattern pattern = Pattern.compile("([0][1-9]|[1][0-2])(-{1})(\\d{1,})");
        Matcher patternPeriodMatcher = pattern.matcher(period);
        if (!patternPeriodMatcher.matches()) {
            throw new BadRequest();
        }

        period = this.employeePayrollService.dateYearMonthFormatter(period);

        for (EmployeePayroll x : employee.getEmployeePaymentList()) {
            if (x.getPeriod().equals(period)) {
                return x;
            }
        }
        throw new BadRequest();
    }

    @Override
    public boolean samePassword(String email, String newPassword) {
        for (Employee x : getAllEmployees()) {
            if (x.getEmail().equals(email)) {
                return encoder.matches(newPassword, x.getPassword());
            }
        }
        return false;
    }

    @Override
    public boolean breachedPassword(String newPassword) {
        for (String x : this.breachedPasswords.listOfBreachedPasswords()) {
            if (x.equals(newPassword)) {
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
