package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ShipShapeNotification extends JPanel {


    private static final String EMAIL_USERNAME = "cravikantha@gmai.com";
    private static final String EMAIL_PASSWORD = "dhfn gywz qool lcjr";


    String url = "jdbc:mysql://localhost:3306/shipshape";
    String user = "root";
    String password = "";


    private JTextField orderIdField;
    private JButton sendButton;

    public ShipShapeNotification() {
        super();


        orderIdField = new JTextField(30); // Larger text box
        sendButton = new JButton("Send Notification");


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);


        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Order ID:"), constraints);

        constraints.gridx = 1;
        panel.add(orderIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(sendButton, constraints);


        add(panel);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = orderIdField.getText().trim();


                if (orderId.isEmpty()) {
                    JOptionPane.showMessageDialog(ShipShapeNotification.this,
                            "Please fill in the Order ID field.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CustomerInfo customerInfo = getCustomerInfo(orderId);
                            if (customerInfo != null) {
                                sendNotification(customerInfo.CustomerMail, orderId, customerInfo.CustomerName);
                            } else {
                                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ShipShapeNotification.this,
                                        "Order information not found.", "Error", JOptionPane.ERROR_MESSAGE));
                            }
                        }
                    }).start();
                }
            }
        });
    }


    class CustomerInfo {
        String CustomerMail;
        String CustomerName;

        public CustomerInfo(String CustomerMail, String CustomerName) {
            this.CustomerMail = CustomerMail;
            this.CustomerName = CustomerName;
        }
    }


    private CustomerInfo getCustomerInfo(String orderId) {
        CustomerInfo customerInfo = null;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT customer_email, customer_name FROM customer_orders WHERE order_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, orderId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String customerMail = rs.getString("customer_email");
                        String customerName = rs.getString("customer_name");
                        customerInfo = new CustomerInfo(customerMail, customerName);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customerInfo;
    }


    private void sendNotification(String customerMail, String orderId, String customerName) {
        String subject = "Your Ship is Ready for Collection";
        String body = "Dear " + customerName + ",\n\n"
                + "We are pleased to inform you that your ship is now ready for collection. "
                + "Please proceed to our facility to collect your order.\n\n"
                + "Details:\n"
                + "- Order ID: " + orderId + "\n\n"
                + "Please contact us at " + EMAIL_USERNAME + " if you have any questions or further assistance.\n\n"
                + "Thank you for choosing ShipShape for your maritime needs.\n\n"
                + "Best regards,\n"
                + "ShipShape Team";


        sendEmail(customerMail, subject, body);
    }


    private void sendEmail(String recipientEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ShipShapeNotification.this,
                    "Email sent successfully to " + recipientEmail, "Success", JOptionPane.INFORMATION_MESSAGE));

        } catch (MessagingException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ShipShapeNotification.this,
                    "Failed to send email.", "Error", JOptionPane.ERROR_MESSAGE));
        }
    }
}
