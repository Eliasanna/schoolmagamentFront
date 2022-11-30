package com.thales.schoolmanagingjfx.controller;

import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EnteteController implements Initializable  {
    @FXML
    public ImageView imLogo;
    @FXML
    public Label lbName;
    @FXML
    public Label lbMenu;
    @FXML
    public Label lbUtilisateur;
    @FXML
    public Button btnBackMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializetxt();
        initializeButtons();
    }

    private void initializeButtons() {
        this.btnBackMenu.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("accueil");
        });
    }

    private void initializetxt() {
        this.lbUtilisateur.setText(SchoolManagingApplication.getUser().getLogin());
        this.lbName.setText(SchoolManagingApplication.getMySchool().getName());
        //TODO le titre du menu
    }
}
