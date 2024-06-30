package view;

import controllers.SupplierController;
import models.Supplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SupplierView extends JPanel {

    private SupplierController controller;

    private JTextField supplierNameField;
    private JTextField contactPersonField;
    private JTextField contactNumberField;
    private JTextField supplierEmailField;
    private JTextField countryField;
    private JList<Supplier> supplierList;

    public SupplierView() {
        this.controller = new SupplierController();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JLabel nameLabel = new JLabel("Supplier Name:");
        supplierNameField = new JTextField();
        JLabel contactPersonLabel = new JLabel("Part Name:");
        contactPersonField = new JTextField();
        JLabel contactNumberLabel = new JLabel("Contact Number:");
        contactNumberField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        supplierEmailField = new JTextField();
        JLabel countryLabel = new JLabel("Country:");
        countryField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(supplierNameField);
        formPanel.add(contactPersonLabel);
        formPanel.add(contactPersonField);
        formPanel.add(contactNumberLabel);
        formPanel.add(contactNumberField);
        formPanel.add(emailLabel);
        formPanel.add(supplierEmailField);
        formPanel.add(countryLabel);
        formPanel.add(countryField);

        JButton addButton = new JButton("Add Supplier");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplierName = supplierNameField.getText().trim();
                String contactPerson = contactPersonField.getText().trim();
                String contactNumber = contactNumberField.getText().trim();
                String supplierEmail = supplierEmailField.getText().trim();
                String country = countryField.getText().trim();

                Supplier supplier = new Supplier();
                supplier.setSupplierName(supplierName);
                supplier.setContactPerson(contactPerson);
                supplier.setContactNumber(contactNumber);
                supplier.setSupplierEmail(supplierEmail);
                supplier.setCountry(country);

                controller.addSupplier(supplier);
                refreshSupplierList();
                clearFormFields();

            }
        });

        JButton updateButton = new JButton("Update Supplier");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier selectedSupplier = supplierList.getSelectedValue();
                if (selectedSupplier != null) {
                    String newName = supplierNameField.getText().trim();
                    String newContactPerson = contactPersonField.getText().trim();
                    String newContactNumber = contactNumberField.getText().trim();
                    String newEmail = supplierEmailField.getText().trim();
                    String newCountry = countryField.getText().trim();

                    selectedSupplier.setSupplierName(newName);
                    selectedSupplier.setContactPerson(newContactPerson);
                    selectedSupplier.setContactNumber(newContactNumber);
                    selectedSupplier.setSupplierEmail(newEmail);
                    selectedSupplier.setCountry(newCountry);

                    controller.updateSupplier(selectedSupplier);
                    refreshSupplierList();
                    clearFormFields();
                }
            }
        });

        JButton removeButton = new JButton("Remove Supplier");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier selectedSupplier = supplierList.getSelectedValue();
                if (selectedSupplier != null) {
                    int option = JOptionPane.showConfirmDialog(SupplierView.this,
                            "Are you sure you want to delete this supplier?",
                            "Confirm Supplier Deletion", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        controller.removeSupplier(selectedSupplier.getSupplierId());
                        refreshSupplierList();
                        clearFormFields();
                    }
                }
            }
        });

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSupplierList();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        supplierList = new JList<>();
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.addListSelectionListener(e -> {
            Supplier selectedSupplier = supplierList.getSelectedValue();
            if (selectedSupplier != null) {
                supplierNameField.setText(selectedSupplier.getSupplierName());
                contactPersonField.setText(selectedSupplier.getContactPerson());
                contactNumberField.setText(selectedSupplier.getContactNumber());
                supplierEmailField.setText(selectedSupplier.getSupplierEmail());
                countryField.setText(selectedSupplier.getCountry());
            }
        });

        JScrollPane scrollPane = new JScrollPane(supplierList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Suppliers:"), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);


        refreshSupplierList();
    }

    private void refreshSupplierList() {
        DefaultListModel<Supplier> model = new DefaultListModel<>();
        List<Supplier> suppliers = controller.getAllSuppliers();
        for (Supplier supplier : suppliers) {
            model.addElement(supplier);
        }
        supplierList.setModel(model);
    }

    private void clearFormFields() {
        supplierNameField.setText("");
        contactPersonField.setText("");
        contactNumberField.setText("");
        supplierEmailField.setText("");
        countryField.setText("");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
