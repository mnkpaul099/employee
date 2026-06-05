package employee.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import employee.management.dto.EmployeeDto;
import employee.management.exception.ResourceNotFoundException;
import employee.management.model.Employee;
import employee.management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("John");

        employeeDto = new EmployeeDto();
    }

    @Test
    void testGetEmployeeList() throws Exception {

        List<Employee> employeeList = Arrays.asList(employee);

        when(employeeService.getEmployeeList()).thenReturn(employeeList);
        when(objectMapper.writeValueAsString(employeeList))
                .thenReturn("[{\"id\":1,\"name\":\"John\"}]");

        ResponseEntity<List<Employee>> response =
                employeeController.getEmployeeList();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList, response.getBody());

        verify(employeeService).getEmployeeList();
        verify(objectMapper).writeValueAsString(employeeList);
    }

    @Test
    void testAddEmployee() {

        when(employeeService.addEmployee(employeeDto))
                .thenReturn(employee);

        ResponseEntity<Employee> response =
                employeeController.addEmployee(employeeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());

        verify(employeeService).addEmployee(employeeDto);
    }

    @Test
    void testSearchEmployee() throws ResourceNotFoundException {

        when(employeeService.searchEmployee(1))
                .thenReturn(employee);

        ResponseEntity<Employee> response =
                employeeController.searchEmployee(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());

        verify(employeeService).searchEmployee(1);
    }

    @Test
    void testSearchEmployeeThrowsException() throws ResourceNotFoundException {

        when(employeeService.searchEmployee(1))
                .thenThrow(new ResourceNotFoundException("Employee not found"));

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.searchEmployee(1));

        verify(employeeService).searchEmployee(1);
    }
}
