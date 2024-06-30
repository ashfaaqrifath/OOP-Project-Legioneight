package view;

import controllers.EmployeeController;
import models.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeView extends JPanel{

    private EmployeeController controller;

    private JTextField fullNameField;
    private JTextField jobRoleField;
    private JTextField contactNumberField;
    private JTextField emailAddressField;
    private JList<Employee> employeeList;

    public EmployeeView() {
        this.controller = new EmployeeController();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Full name: ");
        fullNameField = new JTextField();
        JLabel jobRoleLabel = new JLabel("Job role: ");
        jobRoleField = new JTextField();
        JLabel contactNumberLabel = new JLabel("Contact number: ");
        contactNumberField = new JTextField();
        JLabel emailAddressLabel = new JLabel("Email address: ");
        emailAddressField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(fullNameField);
        formPanel.add(jobRoleLabel);
        formPanel.add(jobRoleField);
        formPanel.add(contactNumberLabel);
        formPanel.add(contactNumberField);
        formPanel.add(emailAddressLabel);
        formPanel.add(emailAddressField);

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText().trim();
                String jobRole = jobRoleField.getText().trim();
                String contactNumber = contactNumberField.getText().trim();
                String emailAddress = emailAddressField.getText().trim();

                Employee employee = new Employee();
                employee.setFullName(fullName);
                employee.setJobRole(jobRole);
                employee.setContactNumber(contactNumber);
                employee.setEmailAddress(emailAddress);

                controller.addEmployee(employee);
                refreshEmployeeList();
                clearFormFields();
            }
        });

        JButton updateButton = new JButton("Update Employee");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee selectedEmployee = employeeList.getSelectedValue();
                if (selectedEmployee != null) {
                    String newName = fullNameField.getText().trim();
                    String newJobRole = jobRoleField.getText().trim();
                    String newContactNumber = contactNumberField.getText().trim();
                    String newEmailAddress = emailAddressField.getText().trim();

                    selectedEmployee.setFullName(newName);
                    selectedEmployee.setJobRole(newJobRole);
                    selectedEmployee.setContactNumber(newContactNumber);
                    selectedEmployee.setEmailAddress(newEmailAddress);

                    controller.updateEmployee(selectedEmployee);
                    refreshEmployeeList();
                    clearFormFields();
                }
            }
        });

        JButton removeButton = new JButton("Remove Employee");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee selectedEmployee = employeeList.getSelectedValue();
                if (selectedEmployee != null) {
                    int option = JOptionPane.showConfirmDialog(EmployeeView.this,
                            "Are you sure you want to delete this employee?",
                            "Confirm employee delete", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        controller.removeEmployee(selectedEmployee.getEmployeeId());
                        refreshEmployeeList();
                        clearFormFields();
                    }
                }
            }
        });

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEmployeeList();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        employeeList = new JList<>();
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeList.addListSelectionListener(e -> {
            Employee selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee != null) {
                fullNameField.setText(selectedEmployee.getFullName());
                jobRoleField.setText(selectedEmployee.getJobRole());
                contactNumberField.setText(selectedEmployee.getContactNumber());
                emailAddressField.setText(selectedEmployee.getEmailAddress());
            }
        });

        JScrollPane scrollPane = new JScrollPane(employeeList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Employee list: "), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);


        refreshEmployeeList();
        clearFormFields();
    }

    private void refreshEmployeeList() {
        DefaultListModel<Employee> model = new DefaultListModel<>();
        List<Employee> employees = controller.getAllEmployees();
        for (Employee employee : employees) {
            model.addElement(employee);
        }
        employeeList.setModel(model);
    }

    private void clearFormFields() {
        fullNameField.setText("");
        jobRoleField.setText("");
        contactNumberField.setText("");
        emailAddressField.setText("");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
