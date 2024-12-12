package nl.com.ems.employeedatabase.common.data.repository;

import nl.com.ems.employeedatabase.common.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}