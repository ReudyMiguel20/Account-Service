package account.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @Column(name = "username", columnDefinition = "VARCHAR_IGNORECASE")
    private String username;


    @Column(name = "authority")
    private String role;

    public Authorities() {
    }

    public Authorities(Employee employee) {
        this.username = employee.getEmail();
        this.role = employee.getRole();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
