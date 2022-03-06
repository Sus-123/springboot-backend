package net.javaguides.springboot.controller;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employees") // if requests are of different method, same api can be used with all
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    //building create employee rest api

    @PostMapping // here we did not took emp in mapping parameter like id, but req body did automatically
    public Employee createEmployee (@RequestBody Employee emp) {  //RequestBody convert json into java obj

        return employeeRepository.save(emp);
    }


    //building getEmployeeById employee rest api

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable long id) {  //PathVariable will bind the mapping variable to parameter id

         Employee employee = employeeRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id : "+ id));


         return ResponseEntity.ok(employee);
    }


    //building update Employee by Id employee rest api

    @PutMapping("{id}") //whenever need to update employee, and post to create new employee
    public ResponseEntity<Employee> UpdateEmployeeById (@PathVariable long id,  @RequestBody Employee newEmployee) {

        Employee updated = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee doesnot exist with id : "+ id));


        updated.setFirstName(newEmployee.getFirstName());
        updated.setLastName(newEmployee.getLastName());
        updated.setEmailId(newEmployee.getEmailId());

        employeeRepository.save(updated);

        return ResponseEntity.ok(updated);


    }


    //building delete Employee by Id employee rest api
    @DeleteMapping("{id}") //whenever need to update employee, and post to create new employee
    public ResponseEntity<Employee> DeleteEmployeeById (@PathVariable long id) {

        Employee updated = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee doesnot exist with id : "+ id));


        employeeRepository.delete(updated); // no return value

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }



}
