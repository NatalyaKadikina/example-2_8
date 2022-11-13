package com.sky.pro.application.service;

import com.sky.pro.application.exception.EmployeeAlreadyAddedException;
import com.sky.pro.application.exception.EmployeeNotFoundException;
import com.sky.pro.application.exception.EmployeeStorageIsFullException;
import com.sky.pro.application.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class EmployeeService {

    //private Map<String, Employee> employees = new HashMap<>();
    private int maxList = 15;

    private final List<Employee> employees = new ArrayList<>();

    private final ValidatorService validatorService;

    public EmployeeService(ValidatorService validatorService) {
       /* employees.put(new Employee("Ivanov", "Ivan", 1,10000).getFullName(), new Employee("Ivanov", "Ivan", 1,10000));
        employees.put(new Employee("Ivanov2", "Ivan2", 2,20000).getFullName(), new Employee("Ivanov2", "Ivan2", 2,20000));
        employees.put(new Employee("Ivanov3", "Ivan3", 3,30000).getFullName(), new Employee("Ivanov3", "Ivan3", 3,30000));
        employees.put(new Employee("Ivanov4", "Ivan4", 4,40000).getFullName(), new Employee("Ivanov4", "Ivan4", 4,40000));
        employees.put(new Employee("Ivanov5", "Ivan5", 1,50000).getFullName(), new Employee("Ivanov5", "Ivan5", 1,50000));
        employees.put(new Employee("Ivanov6", "Ivan6", 2,60000).getFullName(), new Employee("Ivanov6", "Ivan6", 2,60000));
        employees.put(new Employee("Ivanov7", "Ivan7", 3,15000).getFullName(), new Employee("Ivanov7", "Ivan7", 3,15000));
        employees.put(new Employee("Ivanov8", "Ivan8", 4,25000).getFullName(), new Employee("Ivanov8", "Ivan8", 4,25000));
        employees.put(new Employee("Ivanov9", "Ivan9", 1,35000).getFullName(), new Employee("Ivanov9", "Ivan9", 1,35000));
        employees.put(new Employee("Ivanov10", "Ivan10", 2,45000).getFullName(), new Employee("Ivanov10", "Ivan10", 2,45000));*/
        this.validatorService = validatorService;
    }

    public Collection<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    public Employee add(String lastName, String firstName, int department, double salary) {
        Employee newEmployee = new Employee(
                validatorService.validateSurname(lastName),
                validatorService.validateName(firstName),
                department,
                salary);

        if(employees.contains(newEmployee))
            throw new EmployeeAlreadyAddedException("Такой сотрудник есть");

        if(employees.size() + 1 > maxList)
            throw new EmployeeStorageIsFullException("Массив переполнен");

        employees.add(newEmployee);
        return newEmployee;
    }

    public Employee remove(String lastName, String firstName) {

        Employee removeEmployee = new Employee(lastName,firstName);
        if(employees.contains(removeEmployee))
            employees.remove(removeEmployee);
        else
            throw new EmployeeNotFoundException("Не найден работник");
        return removeEmployee;
    }

    public Employee find( String lastName, String firstName) {

        Employee findEmployee = new Employee(lastName, firstName);
        if(!employees.contains(findEmployee))
            throw new EmployeeNotFoundException("Не найден работник");

        return findEmployee;
    }
}
