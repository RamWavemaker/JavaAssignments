package Models;

public interface Employee {
    public int getEmpID();

    public void setEmpID(int empID);

    public String getEmpName();

    void setEmpName(String empName);

    Department getDepartment();

    void setDepartment(Department department);

    Address getAddress();

    void setAddress(Address address);

    void setAddress(Addressimp address);

}
