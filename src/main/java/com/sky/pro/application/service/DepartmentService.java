package com.sky.pro.application.service;

import com.sky.pro.application.exception.EmployeeNotFoundException;
import com.sky.pro.application.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findEmloyeeWithMaxSalaryFromDepartment(int departament) {
        return employeeService.All().stream()
                .filter(employee -> employee.getDepartment() == departament)
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Ошибка"));
    }

    public Employee findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.All().stream()
                .filter(employee -> employee.getDepartment() == department)
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Ошибка"));
    }

    public List<Employee> findEmployeeFromDepartment(int department) {
        return employeeService.All().stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> findEmployees() {
        return employeeService.All().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
