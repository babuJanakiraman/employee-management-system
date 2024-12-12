package nl.com.ems.employeedatabase.common.data.repository;

import nl.com.ems.employeedatabase.common.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
