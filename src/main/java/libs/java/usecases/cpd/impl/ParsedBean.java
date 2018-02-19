package libs.java.usecases.cpd.impl;

public class ParsedBean {

	private String name;
	@Override
	public String toString() {
		return "ParsedBean [name=" + name + ", age=" + age + ", empId=" + empId + "]";
	}
	private int age;
	private int empId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
}
