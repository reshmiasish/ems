package com.ems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Department Entity
 * @author reshmivn
 * @since 0.0.1
 */
@Entity
@Table(name="department")
public class Department {
	
	@Id
	@Column(name="department_id")
	private int departmentId;

	@Column(name="department_name")
	private String departmentName;

	@Column(name="department_desc")
	private String departmentDesc;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + ", departmentDesc="
				+ departmentDesc + "]";
	}

}
