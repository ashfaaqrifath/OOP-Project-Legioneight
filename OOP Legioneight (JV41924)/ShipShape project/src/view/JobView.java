package view;

import controllers.EmployeeController;
import controllers.JobController;
import models.Employee;
import models.Job;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JobView extends JPanel{

    private JobController jobController;
    private EmployeeController employeeController;

    private JTextField descriptionField;
    private JTextField customerNameField;
    private JComboBox<Employee> employeeComboBox;
    private JList<Job> jobList;

    public JobView() {
        this.jobController = new JobController();
        this.employeeController = new EmployeeController();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel descriptionLabel = new JLabel("Job description: ");
        descriptionField = new JTextField();
        JLabel customerNameLabel = new JLabel("Customer name: ");
        customerNameField = new JTextField();
        JLabel employeeLabel = new JLabel("Assigned employee: ");
        employeeComboBox = new JComboBox<>();
        loadEmployeeComboBox();

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(customerNameLabel);
        formPanel.add(customerNameField);
        formPanel.add(employeeLabel);
        formPanel.add(employeeComboBox);

        JButton addButton = new JButton("Add Job");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText().trim();
                String customerName = customerNameField.getText().trim();
                Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();

                if (selectedEmployee != null) {
                    Job job = new Job();
                    job.setJobDescription(description);
                    job.setCustomerName(customerName);
                    job.setAssignedEmployee(selectedEmployee.getFullName());

                    jobController.addJob(job);
                    refreshJobList();
                    clearFormFields();
                } else {
                    JOptionPane.showMessageDialog(JobView.this, "Select an employee to assign the job.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton updateButton = new JButton("Update Job");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Job selectedJob = jobList.getSelectedValue();
                if (selectedJob != null) {
                    String newDescription = descriptionField.getText().trim();
                    String newCustomerName = customerNameField.getText().trim();
                    Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();

                    if (selectedEmployee != null) {
                        selectedJob.setJobDescription(newDescription);
                        selectedJob.setCustomerName(newCustomerName);
                        selectedJob.setAssignedEmployee(selectedEmployee.getFullName());

                        jobController.updateJob(selectedJob);
                        refreshJobList();
                        clearFormFields();
                    } else {
                        JOptionPane.showMessageDialog(JobView.this, "Select an employee to assign the job.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        JButton removeButton = new JButton("Remove Job");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Job selectedJob = jobList.getSelectedValue();
                if (selectedJob != null) {
                    int option = JOptionPane.showConfirmDialog(JobView.this,
                            "Are you sure you want to delete this job?",
                            "Confirm job delete", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        jobController.removeJob(selectedJob.getJobId());
                        refreshJobList();
                        clearFormFields();
                    }
                }
            }
        });

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshJobList();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        jobList = new JList<>();
        jobList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jobList.addListSelectionListener(e -> {
            Job selectedJob = jobList.getSelectedValue();
            if (selectedJob != null) {
                descriptionField.setText(selectedJob.getJobDescription());
                customerNameField.setText(selectedJob.getCustomerName());


                for (int i = 0; i < employeeComboBox.getItemCount(); i++) {
                    if (employeeComboBox.getItemAt(i).getFullName().equals(selectedJob.getAssignedEmployee())) {
                        employeeComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(jobList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Job list: "), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);


        refreshJobList();
        clearFormFields();
    }

    private void loadEmployeeComboBox() {
        List<Employee> employees = employeeController.getAllEmployees();
        for (Employee employee : employees) {
            employeeComboBox.addItem(employee);
        }
    }

    private void refreshJobList() {
        DefaultListModel<Job> model = new DefaultListModel<>();
        List<Job> jobs = jobController.getAllJobs();
        for (Job job : jobs) {
            model.addElement(job);
        }
        jobList.setModel(model);
    }

    private void clearFormFields() {
        descriptionField.setText("");
        customerNameField.setText("");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
