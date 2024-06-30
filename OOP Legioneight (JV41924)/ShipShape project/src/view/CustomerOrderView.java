package view;

import controllers.CustomerOrderController;
import models.CustomerOrder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerOrderView extends JPanel {

    private CustomerOrderController controller;
    private JTextField customerNameField;
    private JTextField orderDateField;
    private JTextField orderDetailsField;
    private JTextField orderPriceField;
    private JTextField customerEmailField;
    private JList<CustomerOrder> orderList;

    public CustomerOrderView() {
        this.controller = new CustomerOrderController();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JLabel nameLabel = new JLabel("Customer name: ");
        customerNameField = new JTextField();
        JLabel dateLabel = new JLabel("Order date (yyyy-MM-dd): ");
        orderDateField = new JTextField();
        JLabel detailsLabel = new JLabel("Order details: ");
        orderDetailsField = new JTextField();
        JLabel priceLabel = new JLabel("Price: ");
        orderPriceField = new JTextField();
        JLabel emailLabel = new JLabel("Customer email: ");
        customerEmailField = new JTextField();


        formPanel.add(nameLabel);
        formPanel.add(customerNameField);
        formPanel.add(dateLabel);
        formPanel.add(orderDateField);
        formPanel.add(detailsLabel);
        formPanel.add(orderDetailsField);
        formPanel.add(priceLabel);
        formPanel.add(orderPriceField);
        formPanel.add(emailLabel);
        formPanel.add(customerEmailField);

        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = customerNameField.getText().trim();
                String orderDateStr = orderDateField.getText().trim();
                String orderDetails = orderDetailsField.getText().trim();
                String orderPriceStr = orderPriceField.getText().trim();
                String customerEmail = customerEmailField.getText().trim();
                Double orderPrice = Double.parseDouble(orderPriceStr);
                Date orderDate = parseDate(orderDateStr);

                if (orderDate != null) {
                    CustomerOrder order = new CustomerOrder();
                    order.setCustomerName(customerName);
                    order.setOrderDate(orderDate);
                    order.setOrderDetails(orderDetails);
                    order.setPrice(orderPrice);
                    order.setCustomerEmail(customerEmail);
                    controller.addCustomerOrder(order);
                    refreshOrderList();
                    clearFormFields();
                } else {
                    JOptionPane.showMessageDialog(CustomerOrderView.this, "Invalid date. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton updateButton = new JButton("Update Order");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerOrder selectedOrder = orderList.getSelectedValue();
                if (selectedOrder != null) {
                    String customerName = customerNameField.getText().trim();
                    String orderDateStr = orderDateField.getText().trim();
                    String orderDetails = orderDetailsField.getText().trim();
                    Date orderDate = parseDate(orderDateStr);
                    String orderPriceStr = orderPriceField.getText().trim();
                    Double orderPrice = Double.parseDouble(orderPriceStr);
                    String customerEmail = customerEmailField.getText().trim();

                    if (orderDate != null) {
                        selectedOrder.setCustomerName(customerName);
                        selectedOrder.setOrderDate(orderDate);
                        selectedOrder.setOrderDetails(orderDetails);
                        selectedOrder.setPrice(orderPrice);
                        selectedOrder.setCustomerEmail(customerEmail);
                        controller.updateCustomerOrder(selectedOrder);
                        refreshOrderList();
                        clearFormFields();
                    } else {
                        JOptionPane.showMessageDialog(CustomerOrderView.this, "Invalid date. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton removeButton = new JButton("Remove Order");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerOrder selectedOrder = orderList.getSelectedValue();
                if (selectedOrder != null) {
                    int option = JOptionPane.showConfirmDialog(CustomerOrderView.this, "Are you sure you want to delete this order?", "Confirm order delete", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        controller.removeCustomerOrder(selectedOrder.getOrderId());
                        refreshOrderList();
                        clearFormFields();
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        orderList = new JList<>();
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CustomerOrder selectedOrder = orderList.getSelectedValue();
                if (selectedOrder != null) {
                    customerNameField.setText(selectedOrder.getCustomerName());
                    orderDateField.setText(formatDate(selectedOrder.getOrderDate()));
                    orderDetailsField.setText(selectedOrder.getOrderDetails());
                    orderPriceField.setText(String.valueOf(selectedOrder.getPrice()));
                    customerEmailField.setText(selectedOrder.getCustomerEmail());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Orders: "), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);


        refreshOrderList();
    }

    private void refreshOrderList() {
        DefaultListModel<CustomerOrder> model = new DefaultListModel<>();
        List<CustomerOrder> orders = controller.getAllCustomerOrders();
        for (CustomerOrder order : orders) {
            model.addElement(order);
        }
        orderList.setModel(model);
    }

    private void clearFormFields() {
        customerNameField.setText("");
        orderDateField.setText("");
        orderDetailsField.setText("");
        orderPriceField.setText("");
        customerEmailField.setText("");
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}
