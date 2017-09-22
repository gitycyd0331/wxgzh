package top.caoyd.www.pojo;

public class Emp {

	private String empId;
	private String empName;
	private String empPhoto;
	private String empHiredate;
	private String empSex;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getEmpHiredate() {
		return empHiredate;
	}

	public void setEmpHiredate(String empHiredate) {
		this.empHiredate = empHiredate;
	}

	public String getEmpSex() {
		return empSex;
	}

	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}

	@Override
	public String toString() {
		return "Emp [empId=" + empId + ", empName=" + empName + ", empPhoto="
				+ empPhoto + ", empHiredate=" + empHiredate + ", empSex="
				+ empSex + "]";
	}

}
