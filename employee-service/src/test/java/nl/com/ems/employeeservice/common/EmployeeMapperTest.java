package nl.com.ems.employeeservice.common;

import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.model.Employee;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeMapperTest {

    private final EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void employeeToEmployeeRequest_shouldMapEmployeeToEmployeeRequest() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");

        EmployeeRequest employeeRequest = mapper.employeeToEmployeeRequest(employee);

        assertEquals("John Doe", employeeRequest.getName());
    }

    @Test
    void employeeResponseToEmployee_shouldMapEmployeeResponseToEmployee() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setName("John Doe");

        Employee employee = mapper.employeeResponseToEmployee(employeeResponse);

        assertEquals("John", employee.getFirstname());
        assertEquals("Doe", employee.getSurname());
    }

    @Test
    void fullName_shouldReturnFullName() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");

        String fullName = mapper.fullName(employee);

        assertEquals("John Doe", fullName);
    }

    @Test
    void firstname_shouldReturnFirstName() {
        String name = "John Doe";

        String firstname = mapper.firstname(name);

        assertEquals("John", firstname);
    }

    @Test
    void surname_shouldReturnSurname() {
        String name = "John Doe";

        String surname = mapper.surname(name);

        assertEquals("Doe", surname);
    }

    @Test
    void firstname_shouldReturnEmptyStringWhenNameIsEmpty() {
        String name = "";

        String firstname = mapper.firstname(name);

        assertEquals("", firstname);
    }

    @Test
    void surname_shouldReturnEmptyStringWhenNameIsEmpty() {
        String name = "";

        String surname = mapper.surname(name);

        assertEquals("", surname);
    }

    @Test
    void firstname_shouldReturnFirstNameWhenNameHasNoSpace() {
        String name = "John";

        String firstname = mapper.firstname(name);

        assertEquals("John", firstname);
    }

    @Test
    void surname_shouldReturnEmptyStringWhenNameHasNoSpace() {
        String name = "John";

        String surname = mapper.surname(name);

        assertEquals("", surname);
    }
}