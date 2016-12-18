package com.ems.service;

import java.sql.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.model.Role;

public class EmployeeServiceTest {
	@Mock
	private EmployeeDAO employeeDAO;
	
	@Mock
	private Employee mockEmployee, mockToPatchEmployee;
	
	@Mock
	private Department mockDepartment, mockUpdateDepartment;
	
	@Mock
	private Role mockRole, mockUpdateRole;
	
	@InjectMocks
	private EmployeeService employeeService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testReadEmployee() {
		mockEmployee.setEmployeeId(100001);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Jeffer");
		mockEmployee.setBirthDate(new Date(1980-02-10));
		mockEmployee.setJoinDate(new Date(2010-01-20));
		mockDepartment.setDepartmentId(2);
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee.setEmail("johnjeffer@ems.com");
		mockEmployee.setSalary(50000);
		mockRole.setRoleId(1);
		mockEmployee.setRole(mockRole);
		mockEmployee.setGender("Male");
		Mockito.when(employeeService.readEmployee(100001)).thenReturn(mockEmployee);
		Assert.assertEquals(mockEmployee, employeeService.readEmployee(100001));
	}
	
	@Test
	public void testAddEmployee() {
		mockEmployee.setEmployeeId(100008);
		mockEmployee.setFirstName("Gracy1");
		mockEmployee.setLastName("Dash1");
		mockEmployee.setBirthDate(new Date(1990-01-07));
		mockEmployee.setJoinDate(new Date(2012-02-04));
		mockDepartment.setDepartmentId(1);
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee.setEmail("gracy8@ems.com");
		mockEmployee.setSalary(60000);
		mockRole.setRoleId(1);
		mockEmployee.setRole(mockRole);
		mockEmployee.setGender("Female");
		employeeService.addEmployee(mockEmployee);
		Mockito.when(employeeService.readEmployee(100008)).thenReturn(mockEmployee);
		Assert.assertEquals(mockEmployee, employeeService.readEmployee(100008));
		}

	@Test
	public void testUpdateEmployee() {
		mockEmployee.setEmployeeId(100001);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Jeffer");
		mockEmployee.setBirthDate(new Date(1980-02-10));
		mockEmployee.setJoinDate(new Date(2010-01-20));
		mockDepartment.setDepartmentId(2);
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee.setEmail("johnjeffer@ems.com");
		mockEmployee.setSalary(50000);
		mockRole.setRoleId(1);
		mockEmployee.setRole(mockRole);
		mockEmployee.setGender("Male");
		Mockito.when(employeeDAO.findEmployeeByEmployeeId(100001)).thenReturn(mockEmployee);
		mockEmployee.setSalary(60000);
		employeeService.updateEmployee(mockEmployee);
		Mockito.when(employeeService.readEmployee(100001)).thenReturn(mockEmployee);
		Assert.assertEquals(mockEmployee, employeeService.readEmployee(100001));
	}
	
	@Test
	public void testPatchEmployee() {
		mockEmployee.setEmployeeId(100001);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Jeffer");
		mockEmployee.setBirthDate(new Date(1980-02-10));
		mockEmployee.setJoinDate(new Date(2010-01-20));
		mockDepartment.setDepartmentId(2);
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee.setEmail("johnjeffer@ems.com");
		mockEmployee.setSalary(50000);
		mockRole.setRoleId(1);
		mockEmployee.setRole(mockRole);
		mockEmployee.setGender("Male");
		Mockito.when(employeeDAO.findEmployeeByEmployeeId(100001)).thenReturn(mockEmployee);
		mockToPatchEmployee.setSalary(60000);
		mockEmployee.setEmployeeId(100001);
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Jeffer");
		mockEmployee.setBirthDate(new Date(1980-02-10));
		mockEmployee.setJoinDate(new Date(2010-01-20));
		mockDepartment.setDepartmentId(2);
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee.setEmail("johnjeffer@ems.com");
		mockEmployee.setSalary(60000);
		mockRole.setRoleId(1);
		mockEmployee.setRole(mockRole);
		mockEmployee.setGender("Male");
		employeeService.patchEmployee(mockToPatchEmployee, mockEmployee);
		Mockito.when(employeeService.readEmployee(100001)).thenReturn(mockEmployee);
		Assert.assertEquals(mockEmployee, employeeService.readEmployee(100001));
	}
	
	@Test
	public void testDeleteEmployee() {
		employeeService.deleteEmployee(100001);
		Mockito.when(employeeService.readEmployee(100001)).thenReturn(null);
		Assert.assertEquals(null, employeeService.readEmployee(100001));
	}
}
