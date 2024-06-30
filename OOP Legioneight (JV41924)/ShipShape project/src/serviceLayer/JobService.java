package serviceLayer;

import database.database;
import models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobService {

    private Connection connection;

    public JobService() {
        this.connection = database.getConnection();
    }

    public void addJob(Job job) {
        String sql = "INSERT INTO jobs (job_description, customer_name, assigned_employee) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, job.getJobDescription());
            statement.setString(2, job.getCustomerName());
            statement.setString(3, job.getAssignedEmployee());
            statement.executeUpdate();


            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int jobId = generatedKeys.getInt(1);
                job.setJobId(jobId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateJob(Job job) {
        String sql = "UPDATE jobs SET job_description=?, customer_name=?, assigned_employee=? WHERE job_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, job.getJobDescription());
            statement.setString(2, job.getCustomerName());
            statement.setString(3, job.getAssignedEmployee());
            statement.setInt(4, job.getJobId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeJob(int jobId) {
        String sql = "DELETE FROM jobs WHERE job_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jobId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Job job = new Job();
                job.setJobId(resultSet.getInt("job_id"));
                job.setJobDescription(resultSet.getString("job_description"));
                job.setCustomerName(resultSet.getString("customer_name"));
                job.setAssignedEmployee(resultSet.getString("assigned_employee"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
}
