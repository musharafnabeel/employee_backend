package employeeServer.employeeServer.repository;

import org.springframework.stereotype.Repository;
import employeeServer.employeeServer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
