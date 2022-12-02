package com.thales.schoolmanagingjfx.controller;

import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        initializeImage();
        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
            initializetxt();
            initializeButtons();
            initializeImage();
        });
    }

    private void initializeImage() {
        //String imageURL="@../../../images/"+SchoolManagingApplication.getMySchool().getLogo()+".jpg";
       //System.out.println(imageURL);
        String imageURL="C:\\Users\\celin\\IdeaProjects\\schoolManagingJFx\\src\\main\\resources\\images\\"+SchoolManagingApplication.getMySchool().getLogo()+".jpg";
        Image logo = new Image(imageURL);
        imLogo.setImage(logo);
    }

    private void initializeButtons() {
        this.btnBackMenu.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("accueil");
        });
    }

    private void initializetxt() {
        SchoolManagingApplication.connectedUserProperty().addListener((observableValue, user, t1) -> {
            this.lbUtilisateur.setText(SchoolManagingApplication.getConnectedUser().getLogin());
        });
        this.lbUtilisateur.setText(SchoolManagingApplication.getConnectedUser().getLogin());
        this.lbName.setText(SchoolManagingApplication.getMySchool().getName());
        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
                    this.lbName.setText(SchoolManagingApplication.getMySchool().getName());
                });

    }
}
