package com.gl.employeemanagementsystem.controller;

import com.gl.employeemanagementsystem.model.Employee;
import com.gl.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/{id}")
    public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody @Valid Employee employee) {
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Deleted employee with id: " + id);
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/search/{query}")
    public List<Employee> searchEmployees(@PathVariable("query") String query) {
        return employeeService.searchEmployees(query);
    }

    @GetMapping("/sort")
    public List<Employee> findAllBySort(
            @RequestParam(name = "order", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy) {
        // Remove surrounding quotes if present
        sortOrder = sortOrder.replaceAll("^\"|\"$", "");

        // Call the service method to find all employees by sort order
        return employeeService.findAllBySort(sortBy, sortOrder);
    }
}
