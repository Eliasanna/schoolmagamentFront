package com.thales.schoolmanagingjfx;

import com.gluonhq.connect.GluonObservableList;
import com.thales.schoolmanagingjfx.model.Address;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.model.User;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class SchoolManagingApplication extends Application {

    public static AnchorPane root;
    public static Map<String, Node> screens = new HashMap<>();
    private static String currentScreen = "userConnect";
    public static int APPWIDTH = 900;
    public static int APPLENGHT = 500;
    private static User connectedUser;
    private static  ObjectProperty<School> mySchool= new SimpleObjectProperty<School>();
    private static  ObjectProperty<Address> myAdresse= new SimpleObjectProperty<Address>();

    public static Address getMyAdresse() {
        return myAdresse.get();
    }

    public static ObjectProperty<Address> myAdresseProperty() {
        return myAdresse;
    }

    public static void setMyAdresse(Address myAdresse) {
        SchoolManagingApplication.myAdresse.set(myAdresse);
    }

    @Override
    public void start(Stage stage) throws IOException {
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("root.fxml"));
        screens.put("userConnect",(BorderPane) FXMLLoader.load(getClass().getResource("userConnect.fxml")));

        root.getChildren().add(screens.get(currentScreen));

        Scene scene = new Scene(root, APPWIDTH, APPLENGHT);
        stage.setTitle("SchoolManaging");
        System.out.println(getUser());
        stage.setScene(scene);
        stage.show();
    }
    public static void setScreen(String screen){

        if (!screens.keySet().contains(screen)) {
            try {
                screens.put(screen, (BorderPane) FXMLLoader.load(SchoolManagingApplication.class.getResource(screen + ".fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        root.getChildren().remove(screens.get(currentScreen));
        root.getChildren().add(screens.get(screen));
        currentScreen = screen;
    }

    public static void setUser(User user){
        connectedUser = user;
    }

    public static User getUser(){
        return connectedUser;
    }

    public static School getMySchool() {
        return mySchool.get();
    }

    public static ObjectProperty<School> mySchoolProperty() {
        return mySchool;
    }

    public static void setMySchool(School mySchool) {
        SchoolManagingApplication.mySchool.set(mySchool);
    }

    public static void main(String[] args) {
        launch();
    }
}