package Models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Employeeimp implements Employee {
    private int empID;
    private String empName;
    private Department department;
    private Address address;
    private static Logger logger = LoggerFactory.getLogger(Employeeimp.class);

    public Employeeimp(int empID, String empName, Department department, Address address) {
        this.empID = empID;
        this.empName = empName;
        this.department = department;
        this.address = address;
    }

    public Employeeimp(int id, String name, int departmentId, String departmentName, String location, int pin) {
        this.empID = id;
        this.empName = name;
        this.department = new Departmentimp(departmentId, departmentName); // Initialize Department
        this.address = new Addressimp(location,pin);
    }

    @Override
    public int getEmpID() {
        return empID;
    }

    @Override
    public void setEmpID(int empID) {
        this.empID = empID;
    }

    @Override
    public String getEmpName() {
        return empName;
    }

    @Override
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddress(Addressimp address) {
        this.address = address;
    }

    @Override
    public String toString() {
        logger.info("retrived employee" + this.empID);
        return String.format(
                "Employee [ID=%d, Name=%s, DepartmentID=%d, DepartmentName=%s, AddressLocation=%s, AddressPin=%d]",
                empID,
                empName,
                department.getDeptId(),
                department.getDeptName(),
                address.getLocation(),
                address.getPin()
        );
    }
}
