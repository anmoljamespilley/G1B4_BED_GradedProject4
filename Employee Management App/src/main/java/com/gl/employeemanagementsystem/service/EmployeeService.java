package com.gl.employeemanagementsystem.service;

import com.gl.employeemanagementsystem.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Long id);

    List<Employee> searchEmployees(String firstName);

    Employee updateEmployee(Employee updatedEmployee);

    Employee getEmployeeById(Long id);

    List<Employee> findAllBySort(String sortBy, String sortOrder);

}
