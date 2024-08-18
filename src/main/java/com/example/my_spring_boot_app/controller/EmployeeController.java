package com.example.my_spring_boot_app.controller;

import com.example.my_spring_boot_app.dto.ResultDto;
import com.example.my_spring_boot_app.dto.EmployeeDto;
import com.example.my_spring_boot_app.model.Employee;
import com.example.my_spring_boot_app.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employees")
@Api(tags = "Employee Controller")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<ResultDto> getEmployeeDetailsById(@PathVariable Long id) {
        ResultDto employee = employeeService.getEmployeeById(id);
        if ("200".equals(employee.getStatusCode())) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else if ("404".equals(employee.getStatusCode())) {
            return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResultDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        ResultDto resultDto = employeeService.registerEmployee(employeeDto);
        if("200".equalsIgnoreCase(resultDto.getStatusCode())) {
            return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("getData")
    public ResponseEntity<String> getData(){
        return ResponseEntity.ok(employeeService.getData());
    }

    public ResponseEntity<Page<Employee>> getEmployee(@RequestParam int page,
                                                      @RequestParam int size){
        return ResponseEntity.ok(employeeService.getEmployees(page, size));
    }
}
