package employee.management.service;

import employee.management.dto.EmployeeDto;
import employee.management.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();

    Employee addEmployee(EmployeeDto employeeDto);

    Employee searchEmployee(int id);

    void deleteEmployee(int id);
}
