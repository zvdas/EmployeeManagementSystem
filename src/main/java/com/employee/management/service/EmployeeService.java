package com.employee.management.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.employee.management.model.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeId(long id);
	Page<Employee> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection);

}
