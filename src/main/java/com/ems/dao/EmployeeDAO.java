package com.ems.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ems.model.Employee;

/**
 * Employee DAO
 * @author reshmivn
 * @since 0.0.1
 */

@Repository
public class EmployeeDAO extends GenericDAO<Employee>{
	
	@Transactional
	public void update(Employee employee) {
		getSession().merge(employee);
	}
	
	@Transactional
	public void persist(Employee employee) {
		getSession().save(employee);
	}
	
	@Transactional
	public Employee findEmployeeByEmployeeId(long employeeId) {
		return (Employee)getSession().get(Employee.class, employeeId);
	}
	
	@Transactional
	public Employee findEmployeeByEmail(String emailAddress) {
		return (Employee)getSession().get(Employee.class, emailAddress);
	}
	@Transactional
	public void removeEmployee(long employeeId) {
		getSession().createQuery("delete from Employee where employeeId=" + employeeId).executeUpdate();
	}
}
