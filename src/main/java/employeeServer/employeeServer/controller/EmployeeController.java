package employeeServer.employeeServer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeServer.employeeServer.entity.Employee;
import employeeServer.employeeServer.repository.EmployeeRepository;
import employeeServer.employeeServer.service.EmployeeDAO;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDAO employeeDAO;


    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long employeeId){
        Employee employeeDetail = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok().body(employeeDetail);
    }

    @PostMapping("/createOrUpdateEmployee")
    public Employee creatOrUpdateEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("deleteEmployee/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId){
        Employee employeeIns = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employeeIns);
        Map<String, Boolean> resMap = new HashMap<>();
        resMap.put("deleted", Boolean.TRUE);
        return resMap;
    }

    @GetMapping("/employeeFilter/{age}/{title}")
	public List<Employee> employeeFilter(@PathVariable(value = "age") Integer age,@PathVariable(value = "title") String title) {
		return employeeDAO.filterEmployee(age,title);
	}
    

}
