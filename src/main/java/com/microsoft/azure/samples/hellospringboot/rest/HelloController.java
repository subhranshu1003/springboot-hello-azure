package com.microsoft.azure.samples.hellospringboot.rest;

import com.microsoft.azure.samples.hellospringboot.entity.EmployeeEntity;
import com.microsoft.azure.samples.hellospringboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {


    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/Greetings")
    public String index() {
        return "Greetings from Spring Boot App! ";
    }


    @GetMapping(value="/bucketlists")
    public List<EmployeeEntity> get() {
        return employeeService.getAllEmployees();
    }

    @PostMapping(value = "/bucketlists")
    public EmployeeEntity save(@RequestBody EmployeeEntity employeeEntity) {
        return employeeService.save(employeeEntity);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/bucketlists/{employeeId}")
    public HttpStatus updateEmployee(@PathVariable Integer employeeId,@RequestBody EmployeeEntity employeeEntity) {

        return  employeeService.updateEmployeeDetail(employeeId,employeeEntity);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/bucketlists/{employeeId}")
    public HttpStatus deletEmployee(@PathVariable Integer employeeId) {
        return employeeService.deleteEmployeeId(employeeId);

    }

    @GetMapping(value="/bucketlists/getCount/")
    public long getCount() {
        return employeeService.getCount();
    }



}
