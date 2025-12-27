package com.mycompany.contactdbapp;

import java.util.List;
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
    }

    private void loadContacts() {
        try {
            contacts = ContactDB.getAllContacts();
            if (!contacts.isEmpty()) {
                currentIndex = 0;
                showContact(contacts.get(currentIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load contacts from DB.", Alert.AlertType.ERROR);
        }
    }

    private void showContact(Contact contact) {
        id.setText(contact.getNational_id());
        firstName.setText(contact.getFirstName());
        middleName.setText(contact.getMiddleName());
        lastName.setText(contact.getLastName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void addNewPersonData(ActionEvent event) {
        
        if (newBtn.getText().equals("New")) {
            grid.setDisable(false);
            id.clear();
            firstName.clear();
            middleName.clear();
            lastName.clear();
            email.clear();
            phone.clear();
            newBtn.setText("Add");
        
        } else if (newBtn.getText().equals("Add")) {
            try {
                Contact contact = new Contact();
                contact.setNational_id(id.getText()); // national_id from TextField
                contact.setFirstName(firstName.getText());
                contact.setMiddleName(middleName.getText());
                contact.setLastName(lastName.getText());
                contact.setEmail(email.getText());
                contact.setPhone(phone.getText());

                int generatedId = ContactDB.addContact(contact); // auto increment ID from DB

                if (generatedId > 0) {
                    contact.setId(generatedId);
                    contacts.add(contact);
                    currentIndex = contacts.size() - 1;

                    showAlert("Success", "Contact added successfully! ", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to add contact.", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "There was a problem. Contact not added successfully.", Alert.AlertType.ERROR);
            }

            grid.setDisable(true);
            newBtn.setText("New");
        }
    }

    @FXML
    private void updatePersonById(ActionEvent event) {
        if (currentIndex >= 0) {
            try {
                Contact contact = contacts.get(currentIndex);
                contact.setNational_id(id.getText()); // update national_id
                contact.setFirstName(firstName.getText());
                contact.setMiddleName(middleName.getText());
                contact.setLastName(lastName.getText());
                contact.setEmail(email.getText());
                contact.setPhone(phone.getText());

                boolean updated = ContactDB.updateContact(contact);
                if (updated) {
                    showAlert("Success", "Contact updated successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to update contact.", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "There was a problem updating the contact.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void deletePersonById(ActionEvent event) {
        if (currentIndex >= 0) {
            try {
                Contact contact = contacts.get(currentIndex);
                boolean deleted = ContactDB.deleteContact(contact.getId());
                if (deleted) {
                    contacts.remove(currentIndex);

                    if (!contacts.isEmpty()) {
                        currentIndex = Math.min(currentIndex, contacts.size() - 1);
                        showContact(contacts.get(currentIndex));
                    } else {
                        currentIndex = -1;
                        id.clear();
                        firstName.clear();
                        middleName.clear();
                        lastName.clear();
                        email.clear();
                        phone.clear();
                    }

                    showAlert("Success", "Contact deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete contact.", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "There was a problem deleting the contact.", Alert.AlertType.ERROR);
            }
        }
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
        if (!contacts.isEmpty() && currentIndex > 0) {
            currentIndex--;
            showContact(contacts.get(currentIndex));
        }
    }

    @FXML
    private void goToNextRecord(ActionEvent event) {
        if (!contacts.isEmpty() && currentIndex < contacts.size() - 1) {
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
