package com.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.model.Role;

/**
 * Service class for Employee
 * @author reshmivn
 * @since 0.0.1
 */
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	/**
	 * Read an employee object from database based on Id	
	 * @param employeeId the employee Id
	 * @return {@link Employee} object
	 */
	public Employee readEmployee(long employeeId) {
		return this.employeeDAO.findEmployeeByEmployeeId(employeeId);
	}
	
	/**
	 * Add an employee by persisting the record in DB
	 * @param emp the {@link Employee} object
	 * @return new added {@link Employee} object
	 */
	public Employee addEmployee(Employee emp) {
		Employee newEmployee = new Employee();
		newEmployee.setEmployeeId(emp.getEmployeeId());
		newEmployee.setFirstName(emp.getFirstName());
		newEmployee.setLastName(emp.getLastName());
		newEmployee.setBirthDate(emp.getBirthDate());
		newEmployee.setJoinDate(emp.getJoinDate());
		newEmployee.setGender(emp.getGender());
		newEmployee.setSalary(emp.getSalary());
		newEmployee.setEmail(emp.getEmail());
		if(emp.getDepartment() != null) {
			Department dept = new Department();
			dept.setDepartmentId(emp.getDepartment().getDepartmentId());
			dept.setDepartmentName(emp.getDepartment().getDepartmentName());
			dept.setDepartmentDesc(emp.getDepartment().getDepartmentDesc());
			newEmployee.setDepartment(dept);
		}
		if(emp.getRole() != null) {
			Role role = new Role();
			role.setRoleId(emp.getRole().getRoleId());
			role.setRoleName(emp.getRole().getRoleName());
			role.setRoleDescription(emp.getRole().getRoleDescription());
			newEmployee.setRole(role);
		}
		employeeDAO.persist(newEmployee);
		return newEmployee;
	}
	
	/**
	 * Updates an employee by persisting the new data into database
	 * @param oldEmp {@link Employee} object
	 * @return modified {@link Employee} object 
	 */
	public Employee updateEmployee(Employee oldEmp) {
			Employee updatedEmployee = new Employee();
			updatedEmployee.setEmployeeId(oldEmp.getEmployeeId());
			updatedEmployee.setFirstName(oldEmp.getFirstName());
			updatedEmployee.setLastName(oldEmp.getLastName());
			updatedEmployee.setBirthDate(oldEmp.getBirthDate());
			updatedEmployee.setGender(oldEmp.getGender());
			updatedEmployee.setJoinDate(oldEmp.getJoinDate());
			updatedEmployee.setSalary(oldEmp.getSalary());
			updatedEmployee.setEmail(oldEmp.getEmail());
			if(oldEmp.getDepartment() != null) {
				Department dept = new Department();
				dept.setDepartmentId(oldEmp.getDepartment().getDepartmentId());
				dept.setDepartmentName(oldEmp.getDepartment().getDepartmentName());
				dept.setDepartmentDesc(oldEmp.getDepartment().getDepartmentDesc());
				updatedEmployee.setDepartment(dept);
			}
			if(oldEmp.getRole() != null) {
				Role role = new Role();
				role.setRoleId(oldEmp.getRole().getRoleId());
				role.setRoleName(oldEmp.getRole().getRoleName());
				role.setRoleDescription(oldEmp.getRole().getRoleDescription());
				updatedEmployee.setRole(role);
			}
			this.employeeDAO.update(updatedEmployee);
			return updatedEmployee;
		}
	
	/**
	 * Updates an employee by persisting the new data into database
	 * @param toPatchEmployee {@link Employee} object with delta changes
	 * @param currentEmployee {@link Employee} current employee data from database
	 * @return modified {@link Employee} object
	 */
	public Employee patchEmployee(Employee toPatchEmployee, Employee currentEmployee) {
		if(toPatchEmployee.getFirstName() != null) {
			currentEmployee.setFirstName(toPatchEmployee.getFirstName());
		}
		if(toPatchEmployee.getLastName() != null) {
			currentEmployee.setLastName(toPatchEmployee.getLastName());
		}
		if(toPatchEmployee.getJoinDate() != null) {
			currentEmployee.setJoinDate(toPatchEmployee.getJoinDate());
		}
		if(toPatchEmployee.getBirthDate() != null) {
			currentEmployee.setBirthDate(toPatchEmployee.getBirthDate());
		}
		if(toPatchEmployee.getGender() != null) {
			currentEmployee.setGender(toPatchEmployee.getGender());
		}
		if(toPatchEmployee.getEmail() != null) {
			currentEmployee.setEmail(toPatchEmployee.getEmail());
		}
		if(toPatchEmployee.getSalary() > 0) {
			currentEmployee.setSalary(toPatchEmployee.getSalary());
		}
		if(toPatchEmployee.getDepartment() != null) {
			if(toPatchEmployee.getDepartment().getDepartmentId() > 0) {
				currentEmployee.getDepartment().setDepartmentId(toPatchEmployee.getDepartment().getDepartmentId());
			}
			if(toPatchEmployee.getDepartment().getDepartmentDesc() != null) {
				currentEmployee.getDepartment().setDepartmentDesc(toPatchEmployee.getDepartment().getDepartmentDesc());
			}
			if(toPatchEmployee.getDepartment().getDepartmentName() != null) {
				currentEmployee.getDepartment().setDepartmentName(toPatchEmployee.getDepartment().getDepartmentName());
			}
		}
		if(toPatchEmployee.getRole() != null) {
			if(toPatchEmployee.getRole().getRoleId() > 0) {
				currentEmployee.getRole().setRoleId(toPatchEmployee.getRole().getRoleId());
			}
			if(toPatchEmployee.getRole().getRoleDescription() != null) {
				currentEmployee.getRole().setRoleDescription(toPatchEmployee.getRole().getRoleDescription());
			}
			if(toPatchEmployee.getRole().getRoleName() != null) {
				currentEmployee.getRole().setRoleName(toPatchEmployee.getRole().getRoleName());
			}
		}
		this.employeeDAO.update(currentEmployee);
		return currentEmployee;
	}

	/**
	 * Delete Employee based on Id
	 * @param employeeId employee Id
	 * @return <code>true</code> if success otherwise <code>false</code>
	 */
	public boolean deleteEmployee(long employeeId) {
		if (employeeId > 0) {
			this.employeeDAO.removeEmployee(employeeId);
			return true;
		} 
		return false;
	}
}
