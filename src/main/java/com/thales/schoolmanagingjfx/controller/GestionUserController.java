package com.thales.schoolmanagingjfx.controller;


import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.User;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {

    @FXML
    public Label lbName;
    @FXML
    public Label lbId;
    @FXML
    public Label lbLogin;
    @FXML
    public Label lbPassword;
    @FXML
    public ImageView imgLogo;
    @FXML
    public Label lbIdUser;
    @FXML
    public TextField txtLogin;
    @FXML
    public TextField txtPassword;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnMenu;
    @FXML
    public Button btnSup;

    User user=SchoolManagingApplication.getUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeText();
        initializeButtons();
    }

    private void initializeText() {
        lbId.setText(String.valueOf(user.getId()));
        lbLogin.setText(user.getLogin());
        lbPassword.setText(user.getPassword());
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            txtLogin.clear();
            txtPassword.clear();
        });

        btnMenu.setOnMouseClicked(mouseEvent -> {
            SchoolManagingApplication.setScreen("accueil");
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            User myUser=new User();
            myUser.setLogin(txtLogin.getText());
            myUser.setPassword(txtPassword.getText());

            HttpRequests.addUser(myUser);

        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            //String id = lbId.getText();
            HttpRequests.deleteUser(String.valueOf(user.getId()));
        });

        /*
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            HttpRequests.update(user);

        });*/
    }


}
