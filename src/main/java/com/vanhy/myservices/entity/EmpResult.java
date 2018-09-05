package com.vanhy.myservices.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class EmpResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigInteger emp_no;
	private String full_name;
	private String gender;

	public EmpResult(BigInteger emp_no, String full_name, String gender) {
		super();
		this.emp_no = emp_no;
		this.full_name = full_name;
		this.gender = gender;
	}
	
	public BigInteger getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(BigInteger emp_no) {
		this.emp_no = emp_no;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
