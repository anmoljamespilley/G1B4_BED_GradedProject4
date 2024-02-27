package com.gl.employeemanagementsystem.serviceimpl;

import com.gl.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.gl.employeemanagementsystem.model.Employee;
import com.gl.employeemanagementsystem.repository.EmployeeRepository;
import com.gl.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String firstName) {
        // Implement the logic to search for employees with the given firstName
        return employeeRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(updatedEmployee.getId());

        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            return employeeRepository.save(existingEmployee);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + updatedEmployee.getId());
        }
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> findAllBySort(String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return employeeRepository.findAll(Sort.by(direction, sortBy));
    }
}
