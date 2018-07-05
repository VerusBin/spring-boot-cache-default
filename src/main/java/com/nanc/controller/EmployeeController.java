package com.nanc.controller;

import com.nanc.entity.Employee;
import com.nanc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chennan
 * @date 2018/7/5 14:38
 */
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employee/{id}")
	public Employee getById(@PathVariable(value = "id") Integer id){
		return employeeService.getById(id);
	}

	@GetMapping("/emp/{lastName}")
	public Employee getByName(@PathVariable(value = "lastName") String lastName){
		return employeeService.getByName(lastName);
	}

	@GetMapping("/employee/update")
	public Object update(@RequestParam(value = "id")Integer id,
	                     @RequestParam(value = "lastName")String lastName,
	                     @RequestParam(value = "email")String email){
		Employee employee = employeeService.getById(id);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employeeService.updateEmp(employee);

		return employee;
	}

	@GetMapping("/save")
	public Object save(Employee employee){
		return employeeService.insertEmp(employee);
	}

	@GetMapping("/delete/{id}")
	public Object delete(@PathVariable(value = "id") Integer id) {
		return employeeService.deleteById(id);
	}
}
