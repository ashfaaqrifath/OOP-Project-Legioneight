package models;

public class Job {

    private int jobId;
    private String jobDescription;
    private String customerName;
    private String assignedEmployee;

    // Constructors, getters, setters
    public Job() {
    }

    public Job(int jobId, String jobDescription, String customerName, String assignedEmployee) {
        this.jobId = jobId;
        this.jobDescription = jobDescription;
        this.customerName = customerName;
        this.assignedEmployee = assignedEmployee;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    @Override
    public String toString() {
        return "jobId=" + jobId +
                ",  Description='" + jobDescription + '\'' +
                ",  CustomerName='" + customerName + '\'' +
                ",  AssignedEmployee='" + assignedEmployee + '\'';
    }
}
