package nl.com.ems.employeeservice.common;


import nl.com.ems.employeeservice.employeedata.model.EmployeeRequest;
import nl.com.ems.employeeservice.employeedata.model.EmployeeResponse;
import nl.com.ems.employeeservice.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "employee", target = "name", qualifiedByName = "fullName")
    EmployeeRequest employeeToEmployeeRequest(Employee employee);

    @Mapping(source = "name", target = "firstname", qualifiedByName = "firstname")
    @Mapping(source = "name", target = "surname", qualifiedByName = "surname")
    Employee employeeResponseToEmployee(EmployeeResponse employeeResponse);

    @Named("fullName")
    default String fullName(Employee employee) {
        return employee.getFirstname() + " " + employee.getSurname();
    }

    @Named("firstname")
    default String firstname(String name) {
        return name.split(" ")[0];
    }

    @Named("surname")
    default String surname(String name) {
        String[] parts = name.split(" ");
        return parts.length > 1 ? parts[1] : "";
    }
}
