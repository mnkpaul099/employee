package employee.management.service;

import employee.management.dto.EmployeeDto;
import employee.management.exception.EmployeeNotFoundException;
import employee.management.model.Employee;
import employee.management.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static employee.management.constants.EmployeeConstants.METHOD_NAME;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeeList() {
        logger.info(METHOD_NAME, "getEmployeeList");
        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        logger.info(METHOD_NAME, "addEmployee");
        Employee employee = Employee.builder()
                .name(employeeDto.getName())
                .emailId(employeeDto.getEmailId())
                .phoneNo(employeeDto.getPhoneNo())
                .department(employeeDto.getDepartment())
                .experience(employeeDto.getExperience())
                .technology(employeeDto.getTechnology())
                .build();
        return employeeRepository.save(employee);
    }

    @Override
    public Employee searchEmployee(int id) {
        logger.info(METHOD_NAME, "searchEmployee");
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee.toString().isEmpty()){
            logger.info("EmployeeId = {} not found", id);
            throw new EmployeeNotFoundException("EmployeeId = " + id + " not found");
        }
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        logger.info(METHOD_NAME, "deleteEmployee");
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee.toString().isEmpty()){
            logger.info("EmployeeId = {} not found", id);
            throw new EmployeeNotFoundException("EmployeeId = " + id + " not found");
        }
        employeeRepository.delete(employee);
    }
}
