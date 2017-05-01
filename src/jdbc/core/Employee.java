package jdbc.core;

import java.math.BigDecimal;

public class Employee {

	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private String department;
	private BigDecimal salary;
	
	public Employee(String lastName, String firstName, String email, String department, BigDecimal salary) {
		this(0, lastName, firstName, email, department, salary);
	}
	
	public Employee(int id, String lastName, String firstName, String email, String department,BigDecimal salary) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.department = department;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format("Employee [id=%s, lastName=%s, firstName=%s, email=%s, department=%s, salary=%s]",
							 id, lastName, firstName, department, email, salary);
	}
		
}
