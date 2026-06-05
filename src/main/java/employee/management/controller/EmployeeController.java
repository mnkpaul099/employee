package employee.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import employee.management.dto.EmployeeDto;
import employee.management.exception.ResourceNotFoundException;
import employee.management.model.Employee;
import employee.management.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static employee.management.constants.EmployeeConstants.METHOD_NAME;

@RestController
@RequestMapping("/v1/employee/")
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @GetMapping("/getEmployeeList")
    public ResponseEntity<List<Employee>> getEmployeeList() throws JsonProcessingException {
        logger.info(METHOD_NAME, "getEmployeeList");
        List<Employee> empList = employeeService.getEmployeeList();
        empList.forEach(emp ->
                logger.info("ID={}, Name={}", emp.getId(), emp.getName())
        );
        logger.info("JSON={}", objectMapper.writeValueAsString(empList));
        return ResponseEntity.ok(empList);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto employeeDto) {
        logger.info(METHOD_NAME, "addEmployee");
        return new ResponseEntity<Employee>(employeeService.addEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> searchEmployee(@PathVariable int id) throws ResourceNotFoundException {
        logger.info(METHOD_NAME, "searchEmployee");
        logger.info("EmployeeId = {}", id);
        return ResponseEntity.ok(employeeService.searchEmployee(id));
    }
}
