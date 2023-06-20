package account.service;

import account.entities.Employee;
import account.entities.User;

import java.util.List;

public interface UserService {

    void save(Employee employee);
    User findUserByUsername(String username);
    List<User> getAllUsers();
}
