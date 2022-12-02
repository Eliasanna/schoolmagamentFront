package com.thales.schoolmanagingjfx.controller;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.User;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserConnectController implements Initializable {
    @FXML
    public TextField txtUser;
    @FXML
    public TextField txtPassWord;
    @FXML
    public Button btnConnect;
    @FXML
    public Label lbError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btnConnect.setOnMouseClicked(mouseEvent -> {
            GluonObservableObject<User> PotentialConnected =
                    HttpRequests.tryLogin(new User(txtUser.getText(), txtPassWord.getText()));

            PotentialConnected.setOnSucceeded(connectStateEvent -> {
                SchoolManagingApplication.setConnectedUser(PotentialConnected.get());

                SchoolManagingApplication.setScreen("accueil");
                txtPassWord.clear();
                txtUser.clear();
                lbError.setVisible(false);
            });
            PotentialConnected.setOnFailed(connectStateEvent -> {
                lbError.setVisible(true);
            });
        });


    }
}
