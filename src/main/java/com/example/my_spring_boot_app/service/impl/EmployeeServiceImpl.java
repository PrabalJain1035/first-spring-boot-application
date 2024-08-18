package com.example.my_spring_boot_app.service.impl;

import com.example.my_spring_boot_app.config.AppConfig;
import com.example.my_spring_boot_app.dto.ResultDto;
import com.example.my_spring_boot_app.dto.EmployeeDto;
import com.example.my_spring_boot_app.model.Employee;
import com.example.my_spring_boot_app.repo.EmployeeRepository;
import com.example.my_spring_boot_app.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    //private final AppConfig appConfig;
    @Autowired
    private RestTemplate restTemplate;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        //this.appConfig = appConfig;
    }

    @Transactional
    @Override
    public ResultDto getEmployeeById(Long id) {
        try {
            Optional<Employee> employee = employeeRepository.findById(id);

            if(employee.isPresent()){
                log.info("employee id : {} found", id);
                return ResultDto.builder()
                        .statusCode("200")
                        .statusDesc("Success")
                        .data(employee.get())
                        .build();
            } else{
                log.info("employee id : {} not found", id);
                return ResultDto.builder()
                        .statusCode("404")
                        .statusDesc("Success")
                        .data("")
                        .build();
            }
        } catch (Exception e) {
            log.error("Employee id not found");
            return ResultDto.builder()
                    .statusCode("500")
                    .statusDesc("Error")
                    .data("")
                    .build();
        }
    }

    @Transactional
    @Override
    public ResultDto registerEmployee(EmployeeDto employeeDto) {
        try {
            Employee emp = Employee.builder()
                    .empName(employeeDto.getName())
                    .empDesignation(employeeDto.getDesignation())
                    .build();

            return ResultDto.builder()
                    .statusCode("200")
                    .statusDesc("Success")
                    .data(employeeRepository.save(emp))
                    .build();
        } catch (Exception e) {
            log.error("error during save employee", e.getStackTrace());
            return ResultDto.builder()
                    .statusCode("500")
                    .statusDesc("Error")
                    .data("")
                    .build();
        }
    }

    @Override
    public String getData() {
        try {
            log.info("calling third party api");
            String url = "https://testurl.com";
            return restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            log.info("error while calling third party api");
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Page<Employee> getEmployees(int page, int size) {
        return employeeRepository.findAll(PageRequest.of(page, size));
    }
}
