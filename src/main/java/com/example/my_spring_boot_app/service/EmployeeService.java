package com.example.my_spring_boot_app.service;

import com.example.my_spring_boot_app.dto.ResultDto;
import com.example.my_spring_boot_app.dto.EmployeeDto;
import com.example.my_spring_boot_app.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    ResultDto getEmployeeById(Long id);
    ResultDto registerEmployee(EmployeeDto employeeDto);
    String getData();
    Page<Employee> getEmployees(int page, int size);
}
