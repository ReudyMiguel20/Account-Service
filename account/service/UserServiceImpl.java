package account.service;

import account.repositories.UserRepository;
import account.entities.Employee;
import account.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(Employee employee) {
        User tempUser = new User(employee);
//        tempUser.setPassword(encoder.encode(tempUser.getPassword()));

        tempUser.setEnabled(1);
        this.userRepository.save(tempUser);
    }

    @Override
    public User findUserByUsername(String username) {
        for (User x : getAllUsers()) {
            if (x.getUsername().equals(username)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }


}
