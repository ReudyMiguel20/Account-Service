package account.service;

import account.entities.Authorities;
import account.entities.Employee;
import account.repositories.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesServiceImpl(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public void save(Employee employee) {
        Authorities tempAuthority = new Authorities(employee);
        tempAuthority.setUsername(employee.getEmail());
        tempAuthority.setRole("ROLE_USER");
        this.authoritiesRepository.save(tempAuthority);
    }
}
