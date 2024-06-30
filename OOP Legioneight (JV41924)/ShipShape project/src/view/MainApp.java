package view;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JPanel currentPanel;

    public MainApp() {
        setTitle("ShipShape Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu taskMenu = new JMenu("Navigation menu");
        menuBar.add(taskMenu);


        JMenuItem manageCustomers = new JMenuItem("Manage Customer Orders");
        manageCustomers.addActionListener(e -> switchPanel(new CustomerOrderView()));
        taskMenu.add(manageCustomers);

        JMenuItem manageSuppliers = new JMenuItem("Manage Suppliers");
        manageSuppliers.addActionListener(e -> switchPanel(new SupplierView()));
        taskMenu.add(manageSuppliers);

        JMenuItem manageInventory = new JMenuItem("Manage Inventory");
        manageInventory.addActionListener(e -> switchPanel(new InventoryView()));
        taskMenu.add(manageInventory);

        JMenuItem manageEmployees = new JMenuItem("Manage Employees");
        manageEmployees.addActionListener(e -> switchPanel(new EmployeeView()));
        taskMenu.add(manageEmployees);

        JMenuItem allocateEmployees = new JMenuItem("Allocate Employee Jobs");
        allocateEmployees.addActionListener(e -> switchPanel(new JobView()));
        taskMenu.add(allocateEmployees);

        JMenuItem salesReports = new JMenuItem("Sales Report");
        salesReports.addActionListener(e -> switchPanel(new SaleReportView()));
        taskMenu.add(salesReports);

        JMenuItem notifyCustomers = new JMenuItem("Notify Customers");
        notifyCustomers.addActionListener(e -> switchPanel(new ShipShapeNotification()));
        taskMenu.add(notifyCustomers);

        JMenuItem notifyEmployees = new JMenuItem("Notify Employees");
        notifyEmployees.addActionListener(e -> switchPanel(new ShipShapeNotificationEmp()));
        taskMenu.add(notifyEmployees);


        switchPanel(new MainPanel());

        setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = panel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}
