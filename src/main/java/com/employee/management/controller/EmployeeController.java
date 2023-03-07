package com.employee.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employee.management.model.Employee;
import com.employee.management.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		/*
		// return all employees without pagination
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
		*/
		
		// return all employees with pagination & sorting
		return findPaginated(1, "firstName", "asc", model);
	}

	// show new employee form
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	// save employee
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}

	// show form for update
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value = "id") long id, Model model) {
		// get employee from service
		Employee employee = employeeService.getEmployeeById(id);
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	// delete employee
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		// delete employee
		employeeService.deleteEmployeeId(id);
		return "redirect:/";
	}

	// handle pagination & sorting
	@GetMapping("/page/{pageNumber}")
	public String findPaginated(
			@PathVariable (value = "pageNumber") int pageNumber,
			@RequestParam ("sortField") String sortField,
			@RequestParam ("sortDirection") String sortDirection,
			Model model
			) {
		// set number of records per page
		int pageSize = 5;
		// find employees by page number & page size
		Page<Employee> page = employeeService.findPaginated(pageNumber, pageSize, sortField, sortDirection);
		// list the employees found above
		List<Employee> listEmployees = page.getContent();
		// add attributes to the index page for pagination
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalRecords", page.getTotalElements());
		// add attributes to the index page for sorting
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		// add attributes to the index page for display
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}

}
