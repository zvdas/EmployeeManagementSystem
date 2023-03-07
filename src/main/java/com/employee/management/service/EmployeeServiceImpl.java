package com.employee.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
		
	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if(optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("Employee with id::"+id+"not found");
		}
		return employee;
	}

	@Override
	public void deleteEmployeeId(long id) {
		employeeRepository.deleteById(id);		
	}

	@Override
	public Page<Employee> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection) {
		// toggle sorting by ascending/descending order using ternary operator
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		// spring data JPA considers pages starting from 0 internally, hence pageNumber - 1
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		return employeeRepository.findAll(pageable);
	}

}
