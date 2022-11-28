module com.example.schoolmanagingjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires lombok;
    requires com.gluonhq.connect;
    requires com.fasterxml.jackson.databind;


    opens com.thales.schoolmanagingjfx to javafx.fxml;
    exports com.thales.schoolmanagingjfx;
    exports com.thales.schoolmanagingjfx.model;
    exports com.thales.schoolmanagingjfx.controller;

}