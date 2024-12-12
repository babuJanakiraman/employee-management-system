package nl.com.ems.employeedatabase.common;

import nl.com.ems.employeedatabase.common.data.entity.Employee;
import nl.com.ems.employeedatabase.common.data.entity.Project;
import nl.com.ems.employeedatabase.common.data.entity.Role;
import nl.com.ems.employeedatabase.model.EmployeeRequest;
import nl.com.ems.employeedatabase.model.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeDataMapper {

    @Mapping(source = "name", target = "firstname", qualifiedByName = "firstname")
    @Mapping(source = "name", target = "surname", qualifiedByName = "surname")
    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);

    @Mapping(source = "employee", target = "name", qualifiedByName = "fullName")
    @Mapping(source = "role", target = "roleId", qualifiedByName = "roleId")
    @Mapping(source = "projects", target = "projectIds", qualifiedByName = "projectIds")
    EmployeeResponse employeeToEmployeeResponse(Employee employee);

    @Named("firstname")
    default String getFirstname(String name) {
        return name.split(" ")[0];
    }

    @Named("surname")
    default String getSurname(String name) {
        String[] parts = name.split(" ");
        return parts.length > 1 ? parts[1] : "";
    }

    @Named("fullName")
    default String getFullName(Employee employee) {
        return employee.getFirstname() + " " + employee.getSurname();
    }

    @Named("roleId")
    default Long getRoleId(Role role) {
        return role == null ? null : role.getId();
    }

    @Named("projectIds")
    default List<Long> getProjectIds(Set<Project> projects) {
        return projects == null ? new ArrayList<>() : projects.stream().map(Project::getId).collect(Collectors.toList());
    }
}