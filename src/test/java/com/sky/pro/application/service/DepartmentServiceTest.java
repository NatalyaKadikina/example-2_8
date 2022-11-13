package com.sky.pro.application.service;

import com.sky.pro.application.exception.EmployeeNotFoundException;
import com.sky.pro.application.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Васильев", "Василий", 1, 55_000),
                new Employee("Андреев", "Андрей", 1, 65_000),
                new Employee("Иванов","Иван",  2, 45_000),
                new Employee("Иванова", "Мария",  2, 50_000),
                new Employee("Андреева","Ирина",  2, 47_000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmloyeeWithMaxSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmloyeeWithMaxSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeWithMinSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeeFromDepartmentPositiveTest(int departmentId, List<Employee> expected) {
        assertThat(departmentService.findEmployeeFromDepartment(departmentId)).containsExactlyElementsOf(expected);
    }

    @Test
    public void employeesGroupedByDepartmentTest() {
        assertThat(departmentService.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Васильев", "Василий", 1, 55_000),
                                new Employee("Андреев","Андрей",  1, 65_000)),
                        2, List.of(new Employee("Иванов","Иван",  2, 45_000),
                                new Employee("Иванова","Мария",  2, 50_000),
                                new Employee("Андреева", "Ирина", 2, 47_000))
                )
        );
    }

    public static Stream<Arguments>employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Андреев", "Андрей",  1, 65_000)),
                Arguments.of(2, new Employee("Иванова", "Мария",  2, 50_000))
        );
    }

    public static Stream<Arguments>employeeWithMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Васильев", "Василий", 1, 55_000)),
                Arguments.of(2, new Employee("Иванов", "Иван", 2, 45_000))
        );
    }

    public static Stream<Arguments>employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Васильев", "Василий", 1, 55_000),
                                new Employee("Андреев", "Андрей", 1, 65_000)),
                Arguments.of(2, List.of(new Employee("Иванов", "Иван", 2, 45_000),
                                new Employee("Иванова", "Мария", 2, 50_000),
                                new Employee("Андреева", "Ирина", 2, 47_000)),
                Arguments.of(3, Collections.emptyList())
        )));
    }
}