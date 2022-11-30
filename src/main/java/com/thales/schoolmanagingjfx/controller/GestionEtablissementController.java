package com.thales.schoolmanagingjfx.controller;

import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionEtablissementController implements Initializable  {
    @FXML
    public Label lbName;
    @FXML
    public TextField tbCity;
    @FXML
    public TextField tbCountry;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button BtnSup;
    @FXML
    public Label lbId;
    @FXML
    public Label lbTel;
    @FXML
    public Label lbStreat;
    @FXML
    public Label lbCity;
    @FXML
    public Label lbCountry;
    @FXML
    public ImageView imgLogo;
    @FXML
    public Label lbIdShcool;
    @FXML
    public ComboBox cbType;
    @FXML
    public TextField tbTel;
    @FXML
    public TextField tbUrlIm;
    @FXML
    public TextField tbnbStreat;
    @FXML
    public TextField tbStreat;
    public HBox enTete;
    public Button btnSup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeButtons();
    }

    private void initializeButtons() {
        btnSup.setOnMouseClicked(mouseEvent -> {
            //String id = lbId.getText();
            //HttpRequests.deleteCourse(id);
            SchoolManagingApplication.setScreen("accueil");
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion de l'établissement  ");
    }

}
