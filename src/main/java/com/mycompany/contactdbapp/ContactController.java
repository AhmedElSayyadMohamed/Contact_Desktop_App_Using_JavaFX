package com.mycompany.contactdbapp;

import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ContactController {

    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;

    @FXML
    private Button newBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button firstBtn;
    @FXML
    private Button previousBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button lastBtn;

    @FXML
    private GridPane grid;

    private List<Contact> contacts;
    private int currentIndex = -1;

    public void initialize() {
        loadContacts();
        grid.setDisable(true);
        updateButtonsState();
    }

    private void loadContacts() {
        try {
            contacts = ContactDB.getAllContacts();
            if (contacts != null && !contacts.isEmpty()) {
                currentIndex = 0;
                showContact(contacts.get(currentIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load contacts from DB.", Alert.AlertType.ERROR);
        }
        updateButtonsState();
    }

    private void showContact(Contact contact) {
        id.setText(contact.getNational_id());
        firstName.setText(contact.getFirstName());
        middleName.setText(contact.getMiddleName());
        lastName.setText(contact.getLastName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        updateButtonsState();
    }

    private void clearFields() {
        id.clear();
        firstName.clear();
        middleName.clear();
        lastName.clear();
        email.clear();
        phone.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateButtonsState() {
        boolean hasContacts = contacts != null && !contacts.isEmpty();
        int size = hasContacts ? contacts.size() : 0;

        // Navigation always visible
        firstBtn.setDisable(!hasContacts || currentIndex == 0);
        previousBtn.setDisable(!hasContacts || currentIndex == 0);
        nextBtn.setDisable(!hasContacts || currentIndex == size - 1);
        lastBtn.setDisable(!hasContacts || currentIndex == size - 1);

        // Update/Delete enabled/disabled
        updateBtn.setDisable(!hasContacts || updateBtn.getText().trim().equals("Save"));
        deleteBtn.setDisable(!hasContacts || newBtn.getText().trim().equals("Add"));
    }

    @FXML
    private void addNewPersonData(ActionEvent event) {
        if (newBtn.getText().trim().equals("New")) {
            grid.setDisable(false);
            clearFields();
            newBtn.setText("Add");

            updateBtn.setDisable(true);
            deleteBtn.setDisable(true);

        } else if (newBtn.getText().trim().equals("Add")) {
            try {
                Contact contact = new Contact();
                contact.setNational_id(id.getText());
                contact.setFirstName(firstName.getText());
                contact.setMiddleName(middleName.getText());
                contact.setLastName(lastName.getText());
                contact.setEmail(email.getText());
                contact.setPhone(phone.getText());

                int generatedId = ContactDB.addContact(contact);
                if (generatedId > 0) {
                    contact.setId(generatedId);
                    contacts.add(contact);
                    currentIndex = contacts.size() - 1;

                    showAlert("Success", "Contact added successfully!", Alert.AlertType.INFORMATION);
                    showContact(contact);
                } else {
                    showAlert("Error", "Failed to add contact.", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Contact not added.", Alert.AlertType.ERROR);
            }

            grid.setDisable(true);
            newBtn.setText("New");
            updateButtonsState();
        }
    }

    @FXML
    private void updatePersonById(ActionEvent event) {
        if (currentIndex < 0) return;

        Contact contact = contacts.get(currentIndex);

        if (updateBtn.getText().trim().equals("Update")) {
            // Enable Grid for editing
            grid.setDisable(false);
            Platform.runLater(() -> updateBtn.setText("Save"));

            newBtn.setDisable(true);
            deleteBtn.setDisable(true);

        } else if (updateBtn.getText().trim().equals("Save")) {
            try {
                contact.setNational_id(id.getText());
                contact.setFirstName(firstName.getText());
                contact.setMiddleName(middleName.getText());
                contact.setLastName(lastName.getText());
                contact.setEmail(email.getText());
                contact.setPhone(phone.getText());

                if (ContactDB.updateContact(contact)) {
                    showAlert("Success", "Contact updated successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to update contact.", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Update failed.", Alert.AlertType.ERROR);
            }

            grid.setDisable(true);
            updateBtn.setText("Update");
            newBtn.setDisable(false);
            deleteBtn.setDisable(false);
            updateButtonsState();
        }
    }

    @FXML
    private void deletePersonById(ActionEvent event) {
        if (currentIndex < 0) return;

        try {
            Contact contact = contacts.get(currentIndex);

            if (ContactDB.deleteContact(contact.getId())) {
                contacts.remove(currentIndex);

                if (!contacts.isEmpty()) {
                    currentIndex = Math.min(currentIndex, contacts.size() - 1);
                    showContact(contacts.get(currentIndex));
                } else {
                    currentIndex = -1;
                    clearFields();
                }

                showAlert("Success", "Contact deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Delete failed.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Delete failed.", Alert.AlertType.ERROR);
        }

        updateButtonsState();
    }

    @FXML
    private void goToFirstRecord(ActionEvent event) {
        if (!contacts.isEmpty()) {
            currentIndex = 0;
            showContact(contacts.get(currentIndex));
        }
    }

    @FXML
    private void goToPreviousRecord(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            showContact(contacts.get(currentIndex));
        }
    }

    @FXML
    private void goToNextRecord(ActionEvent event) {
        if (currentIndex < contacts.size() - 1) {
            currentIndex++;
            showContact(contacts.get(currentIndex));
        }
    }

    @FXML
    private void goToLastRecord(ActionEvent event) {
        if (!contacts.isEmpty()) {
            currentIndex = contacts.size() - 1;
            showContact(contacts.get(currentIndex));
        }
    }
}
