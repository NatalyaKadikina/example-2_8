package com.sky.pro.application.service;

import com.sky.pro.application.exception.*;
import com.sky.pro.application.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    @AfterEach
    public void afterEach() {
        employeeService.getAll().forEach(employee -> employeeService.remove(employee.getLastName(), employee.getFirstName()));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest1(String surname,
                                 String name,
                                 int department,
                                 double salary) {
        Employee expected = new Employee(surname, name, department, salary);

        assertThat(employeeService.getAll()).isEmpty();

        employeeService.add(surname, name, department, salary);

        assertThat(employeeService.getAll())
                .hasSize(1)
                .containsExactly(expected);
        assertThat(employeeService.find(expected.getLastName(), expected.getFirstName()))
                .isNotNull()
                .isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add(surname, name, department, salary));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest2(String surname,
                                 String name,
                                 int department,
                                 double salary) {
        List<Employee> employees = generateEmployees(15);
        employees.forEach(employee ->
                assertThat(employeeService.add(employee.getLastName(), employee.getFirstName(), employee.getDepartment(), employee.getSalary())).isEqualTo(employee)
        );
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add(surname, name, department, salary));
    }

    @Test
    public void addNegativeTest3() {
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.add("Ivanov", "Иван#", 1, 55_000));

        assertThatExceptionOfType(InccorrectSurnameException.class)
                .isThrownBy(() -> employeeService.add("!Петров", "Petr", 1, 65_000));

        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.add("Иванова",null,  2, 75_000));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeNegativeTest(String name,
                                   String surname,
                                   int department,
                                   double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("test", "test"));

        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("test", "test"));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void findPositiveTest(String name,
                                 String surname,
                                 int department,
                                 double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.add(name, surname, department, salary)).isEqualTo(expected);

        assertThat(employeeService.find(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll()).hasSize(1);
    }

    private List<Employee>generateEmployees(int size) {
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employee("Name" + (char) ((int) 'a' + i), "Surname" +(char) ((int) 'a' + i), i, 10_000 + i))
                .collect(Collectors.toList());
    }

    public  static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Ivanov", "Ivan", 1, 55_000),
                Arguments.of("Petrov", "Petr", 1, 65_000),
                Arguments.of("Ivanova", "Mariya", 2, 75_000)
        );
    }


}