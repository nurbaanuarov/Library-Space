module com.example.libraryspaceproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.libraryspaceproject to javafx.fxml;
    exports com.example.libraryspaceproject;
}