package view;

import controllers.SaleReportController;
import models.CustomerOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SaleReportView extends JPanel {

    private SaleReportController saleController;

    private JComboBox<String> monthComboBox;
    private JTextField yearField;
    private JTextArea reportTextArea;
    private JLabel totalSalesLabel;

    public SaleReportView() {
        this.saleController = new SaleReportController();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel monthLabel = new JLabel("Month:");
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField(6);

        JButton generateButton = new JButton("Generate Report");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        inputPanel.add(monthLabel);
        inputPanel.add(monthComboBox);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(generateButton);

        JPanel reportPanel = new JPanel(new BorderLayout());
        reportTextArea = new JTextArea(15, 50);
        reportTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        reportPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel totalSalesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalSalesLabel = new JLabel();
        totalSalesPanel.add(totalSalesLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(reportPanel, BorderLayout.CENTER);
        add(totalSalesPanel, BorderLayout.SOUTH);
    }

    private void generateReport() {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        int monthIndex = monthComboBox.getSelectedIndex() + 1;
        int year = Integer.parseInt(yearField.getText().trim());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthIndex - 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, daysInMonth - 1);
        Date endDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reportHeader = String.format("Sales Report for %s %d\n", selectedMonth, year);
        StringBuilder reportBuilder = new StringBuilder(reportHeader);
        double totalSales = 0;


        List<CustomerOrder> orders = saleController.getMonthlySalesReport(monthIndex, year);
        for (CustomerOrder order : orders) {
            double orderPrice = order.getPrice();
            totalSales += orderPrice;
            String orderDetails = String.format("Date: %s, Customer: %s, Order Details: %s, Price: $%.2f\n",
                    dateFormat.format(order.getOrderDate()), order.getCustomerName(), order.getOrderDetails(), orderPrice);
            reportBuilder.append(orderDetails);
        }

        reportTextArea.setText(reportBuilder.toString());
        totalSalesLabel.setText(String.format("Total Sales: $%.2f", totalSales));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 500);
    }
}
