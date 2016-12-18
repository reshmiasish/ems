package com.ems.model;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * Employee Entity
 * @author reshmivn
 * @since 0.0.1
 */
@Entity
@Table(name="employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	private long employeeId;
	
	@NotNull(message="{employee.first.name.empty}")
	@Size(min=2, max=25, message="{employee.first.name.length}")
	@Column(name="first_name")
	private String firstName;
	
	@Size(min=2, max=25, message="{employee.last.name.length}")
	@Column(name="last_name")
	private String lastName;
	

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="department_id")
	private Department department;

	@NotNull(message="{birthDate.empty}")
	@Column(name="birth_date")
	private Date birthDate;

	@NotNull(message="{joinDate.empty}")
	@Column(name="join_date")
	private Date joinDate;
	

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;

	@NotNull(message="{gender.empty}")
	@Column(name="gender")
	private String gender;

	@NotNull(message="{salary.empty}")
	@javax.validation.constraints.Min(value=1, message="{salary.min}")
	@Column(name="salary")
	private long salary;

	@NotNull(message="{email.empty}")
	@Email(message="{email.format}")
	@Column(name="email")
	private String email;

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", department=" + department + ", birthDate=" + birthDate + ", joinDate=" + joinDate + ", role="
				+ role + ", gender=" + gender + ", salary=" + salary + ", email=" + email + "]";
	}

}
