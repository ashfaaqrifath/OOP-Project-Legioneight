package models;

public class Employee {

    private int employeeId;
    private String fullName;
    private String jobRole;
    private String contactNumber;
    private String emailAddress;

    // Constructors, getters, setters
    public Employee() {
    }

    public Employee(int employeeId, String fullName, String jobRole, String contactNumber, String emailAddress) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.jobRole = jobRole;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "employeeId=" + employeeId +
                ",  fullName='" + fullName + '\'' +
                ",  jobRole='" + jobRole + '\'' +
                ",  contactNumber='" + contactNumber + '\'' +
                ",  emailAddress='" + emailAddress + '\'';
    }
}
