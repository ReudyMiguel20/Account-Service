package account.service;

import account.entities.Authorities;
import account.entities.Employee;
import account.repositories.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    public AuthoritiesServiceImpl(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    /* Pretty self-explanatory method of creating a new Authority, then setting some values to then save it to the
    *  database. */
    @Override
    public void save(Employee employee) {
        Authorities tempAuthority = new Authorities(employee);
        tempAuthority.setUsername(employee.getEmail());
        tempAuthority.setRole("ROLE_USER");
        this.authoritiesRepository.save(tempAuthority);
    }
}
