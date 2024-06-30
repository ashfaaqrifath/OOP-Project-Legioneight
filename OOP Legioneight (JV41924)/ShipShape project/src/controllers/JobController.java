package controllers;

import models.Job;
import serviceLayer.JobService;

import java.util.List;

public class JobController {

    private JobService service;

    public JobController() {
        this.service = new JobService();
    }

    public void addJob(Job job) {
        service.addJob(job);
    }

    public void updateJob(Job job) {
        service.updateJob(job);
    }

    public void removeJob(int jobId) {
        service.removeJob(jobId);
    }

    public List<Job> getAllJobs() {
        return service.getAllJobs();
    }

}
