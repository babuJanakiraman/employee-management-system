package nl.com.ems.employeedatabase.common.data.repository;

import nl.com.ems.employeedatabase.common.data.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
