package view;

import controllers.InventoryController;
import models.InventoryItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryView  extends JPanel {
    private InventoryController controller;

    private JTextField itemNameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JList<InventoryItem> inventoryItemList;

    public InventoryView() {
        this.controller = new InventoryController();

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Item name: ");
        itemNameField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityField = new JTextField();
        JLabel priceLabel = new JLabel("Price: ");
        priceField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(itemNameField);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                InventoryItem item = new InventoryItem();
                item.setItemName(itemName);
                item.setQuantity(quantity);
                item.setPrice(price);

                controller.addInventoryItem(item);
                refreshInventoryItemList();
                clearFormFields();
            }
        });

        JButton updateButton = new JButton("Update Item");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryItem selectedItem = inventoryItemList.getSelectedValue();
                if (selectedItem != null) {
                    String newName = itemNameField.getText().trim();
                    int newQuantity = Integer.parseInt(quantityField.getText().trim());
                    double newPrice = Double.parseDouble(priceField.getText().trim());

                    selectedItem.setItemName(newName);
                    selectedItem.setQuantity(newQuantity);
                    selectedItem.setPrice(newPrice);

                    controller.updateInventoryItem(selectedItem);
                    refreshInventoryItemList();
                    clearFormFields();
                }
            }
        });

        JButton removeButton = new JButton("Remove Item");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryItem selectedItem = inventoryItemList.getSelectedValue();
                if (selectedItem != null) {
                    int option = JOptionPane.showConfirmDialog(InventoryView.this,
                            "Are you sure you want to delete this item?",
                            "Confirm item delete", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        controller.removeInventoryItem(selectedItem.getItemId());
                        refreshInventoryItemList();
                        clearFormFields();
                    }
                }
            }
        });

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshInventoryItemList();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);

        inventoryItemList = new JList<>();
        inventoryItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryItemList.addListSelectionListener(e -> {
            InventoryItem selectedItem = inventoryItemList.getSelectedValue();
            if (selectedItem != null) {
                itemNameField.setText(selectedItem.getItemName());
                quantityField.setText(String.valueOf(selectedItem.getQuantity()));
            }
        });

        JScrollPane scrollPane = new JScrollPane(inventoryItemList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Inventory items: "), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);


        refreshInventoryItemList();
        clearFormFields();
    }

    private void refreshInventoryItemList() {
        DefaultListModel<InventoryItem> model = new DefaultListModel<>();
        List<InventoryItem> items = controller.getAllInventoryItems();
        for (InventoryItem item : items) {
            model.addElement(item);
        }
        inventoryItemList.setModel(model);
    }

    private void clearFormFields() {
        itemNameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }

}
