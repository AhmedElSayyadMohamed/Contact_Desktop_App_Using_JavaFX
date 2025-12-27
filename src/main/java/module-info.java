module com.mycompany.contactdbapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires derbyclient;
    
    opens com.mycompany.contactdbapp to javafx.fxml;
    exports com.mycompany.contactdbapp;
}
