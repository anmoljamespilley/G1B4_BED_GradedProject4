package com.gl.employeemanagementsystem.data;

import com.github.javafaker.Faker;
import com.gl.employeemanagementsystem.model.Employee;
import com.gl.employeemanagementsystem.model.Role;
import com.gl.employeemanagementsystem.model.User;
import com.gl.employeemanagementsystem.repository.EmployeeRepository;
import com.gl.employeemanagementsystem.repository.RoleRepository;
import com.gl.employeemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    @Autowired
    public DataLoader(EmployeeRepository employeeRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) {
        loadEmployees();
        loadRoles();
        loadUsers();
    }

    private void loadEmployees() {
        if (employeeRepository.count() == 0) {
            for (int i = 0; i < 5; i++) {
                Employee employee = new Employee(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress());
                employeeRepository.save(employee);
            }
        }
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role("USER");
            Role adminRole = new Role("ADMIN");
            roleRepository.saveAll(List.of(userRole, adminRole));
        }
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            Role userRole = roleRepository.findByName("USER");
            if (adminRole != null && userRole != null) {
                User adminUser = new User("admin", passwordEncoder.encode("admin"), Collections.singleton(adminRole));
                userRepository.save(adminUser);

                User normalUser = new User("anmol", passwordEncoder.encode("anmol"), Collections.singleton(userRole));
                userRepository.save(normalUser);
            } else {
                throw new IllegalStateException("Roles not found.");
            }
        }
    }
}
