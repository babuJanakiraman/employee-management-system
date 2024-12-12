package nl.com.ems.employeedatabase.common;

import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeDataMapperTest {

    private final EmployeeDataMapper mapper = Mappers.getMapper(EmployeeDataMapper.class);

    @Test
    void employeeRequestToEmployee_shouldMapEmployeeRequestToEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("John Doe");

        Employee employee = mapper.employeeRequestToEmployee(employeeRequest);

        assertEquals("John", employee.getFirstname());
        assertEquals("Doe", employee.getSurname());
    }

    @Test
    void employeeToEmployeeResponse_shouldMapEmployeeToEmployeeResponse() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");

        EmployeeResponse employeeResponse = mapper.employeeToEmployeeResponse(employee);

        assertEquals("John Doe", employeeResponse.getName());
    }

    @Test
    void getFirstname_shouldReturnFirstName() {
        String name = "John Doe";

        String firstname = mapper.getFirstname(name);

        assertEquals("John", firstname);
    }

    @Test
    void getSurname_shouldReturnSurname() {
        String name = "John Doe";

        String surname = mapper.getSurname(name);

        assertEquals("Doe", surname);
    }

    @Test
    void getFirstname_shouldReturnEmptyStringWhenNameIsEmpty() {
        String name = "";

        String firstname = mapper.getFirstname(name);

        assertEquals("", firstname);
    }

    @Test
    void getSurname_shouldReturnEmptyStringWhenNameIsEmpty() {
        String name = "";

        String surname = mapper.getSurname(name);

        assertEquals("", surname);
    }

    @Test
    void getFirstname_shouldReturnFirstNameWhenNameHasNoSpace() {
        String name = "John";

        String firstname = mapper.getFirstname(name);

        assertEquals("John", firstname);
    }

    @Test
    void getSurname_shouldReturnEmptyStringWhenNameHasNoSpace() {
        String name = "John";

        String surname = mapper.getSurname(name);

        assertEquals("", surname);
    }
}
