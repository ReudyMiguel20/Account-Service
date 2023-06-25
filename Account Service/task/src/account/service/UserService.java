package account.service;

import account.dto.SuccessfulPassword;
import account.entities.Employee;
import account.entities.User;

import java.util.List;

public interface UserService {
    void saveNewUser(Employee employee);
    User findUserByUsername(String username);
    void updateUser(User user);
    SuccessfulPassword changePassword(Employee employee);
    void deleteUser(String username);
    List<User> getAllUsers();

}
