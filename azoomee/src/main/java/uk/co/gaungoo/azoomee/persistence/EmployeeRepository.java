package uk.co.gaungoo.azoomee.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.Table;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Optional<Employee> findByName(String name);

	List<Employee> findByDepartment(String department);
}
