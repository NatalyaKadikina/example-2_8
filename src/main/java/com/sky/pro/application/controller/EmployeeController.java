package com.sky.pro.application.controller;

import com.sky.pro.application.model.Employee;
import com.sky.pro.application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public String hello() {
        return "Добро пожаловать";
    }

    @GetMapping("/all")
    public Collection<Employee> all() {
        return  employeeService.All();
    }

    @GetMapping("/remove")
    public Employee remove(@RequestParam(name = "firstName") String firstName,
                        @RequestParam(name = "lastName") String lastName) {
        try {
            return employeeService.Remove(firstName, lastName);
        }catch (RuntimeException ex){
            return null;
        }
    }

    @GetMapping("/add")
    public Employee add(@RequestParam(name = "firstName") String firstName,
                        @RequestParam(name = "lastName") String lastName,
                        @RequestParam(name = "departmentId") int departmentId,
                        @RequestParam(name = "salary") int salary) {
        try {
            return employeeService.Add(firstName, lastName, departmentId, salary);
        }catch (RuntimeException ex){
            return null;
        }
    }

    @GetMapping("/find")
    public Employee find(@RequestParam(name = "firstName") String firstName,
                       @RequestParam(name = "lastName") String lastName) {
        try {
            return employeeService.Find(firstName, lastName);
        }catch (RuntimeException ex){
            return null;
        }
    }

}