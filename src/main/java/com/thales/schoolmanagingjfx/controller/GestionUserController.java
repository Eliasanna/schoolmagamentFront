package com.thales.schoolmanagingjfx.controller;

import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.Grade;
import com.thales.schoolmanagingjfx.model.User;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    User user=SchoolManagingApplication.getConnectedUser();

    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeText();
        initializeButtons();
    }

    private void initializeText() {
        lbId.setText(String.valueOf(user.getId()));

        SchoolManagingApplication.connectedUserProperty().addListener((observableValue, user, t1) -> {
            this.lbLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
            this.lbPassword.setText(SchoolManagingApplication.getConnectedUser().getPassword());
            this.txtLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
            this.lbPassword.setText(SchoolManagingApplication.getConnectedUser().getPassword());
            lbId.setText(String.valueOf(SchoolManagingApplication.getConnectedUser().getId()));
        });
        this.lbLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
        this.lbPassword.setText(SchoolManagingApplication.getConnectedUser().getPassword());
        this.txtLogin.setText(SchoolManagingApplication.getConnectedUser().getLogin());
        this.lbPassword.setText(SchoolManagingApplication.getConnectedUser().getPassword());
        lbId.setText(String.valueOf(SchoolManagingApplication.getConnectedUser().getId()));
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
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
            //TODO a modifier, faut aller chercher en base l'user ajouter pou avoir son ID
            lbId.setVisible(false);
            lbLogin.setText(myUser.getLogin());
            lbPassword.setText(myUser.getPassword());
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            //String id = lbId.getText();
            HttpRequests.deleteUser(String.valueOf(user.getId()));
        });

        btnUpdate.setOnMouseClicked(mouseEvent -> {
            User myUser=new User();
            myUser.setLogin(txtLogin.getText());
            myUser.setPassword(txtPassword.getText());
            String id = lbId.getText();
            int id1= Integer.parseInt(id);
            myUser.setId(id1);
            HttpRequests.addUser(myUser);

            lbLogin.setText(myUser.getLogin());
            lbPassword.setText(myUser.getPassword());
            SchoolManagingApplication.setConnectedUser(myUser);

        });
    }
}
