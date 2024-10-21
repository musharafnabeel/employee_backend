package employeeServer.employeeServer.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import employeeServer.employeeServer.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> filterEmployee(Integer age,String title){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate conditions = criteriaBuilder.conjunction(); 
        String role = title != null ? title.toLowerCase() : null;

        if (role != null) {
            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(employeeRoot.get("title")), "%" + role + "%");
            conditions = criteriaBuilder.and(conditions, titlePredicate);
        }

        if (age != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR, -age);
            Date cutoffDate = calendar.getTime();
            Predicate agePredicate = criteriaBuilder.lessThan(employeeRoot.get("dateOfBirth"), cutoffDate);
            conditions = criteriaBuilder.and(conditions, agePredicate);
        }

        criteriaQuery.where(conditions);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
