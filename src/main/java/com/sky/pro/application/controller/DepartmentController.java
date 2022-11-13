package com.sky.pro.application.controller;

import com.sky.pro.application.model.Employee;
import com.sky.pro.application.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalaryFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmloyeeWithMaxSalaryFromDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalaryFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(department);
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> findEmloyeesFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeFromDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmloyees() {
        return departmentService.findEmployees();
    }


}
