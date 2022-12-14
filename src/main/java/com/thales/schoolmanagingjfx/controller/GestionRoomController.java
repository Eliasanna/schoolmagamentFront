package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.ClassRoom;
import com.thales.schoolmanagingjfx.model.Course;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionRoomController implements Initializable {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtCapacity;
    @FXML
    public ComboBox cbExcluCourse;
    @FXML
    public Button btnAddExcluCourse;
    @FXML
    public Label lbListCourse;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;
    @FXML
    public Label lbId;
    @FXML
    public Button btnClearExcluCourse;
    private School school = SchoolManagingApplication.getMySchool();

    private ObjectProperty<ClassRoom> selectedClassRoom = new SimpleObjectProperty<ClassRoom>();

    private ObservableList<ClassRoom> classRoomss;

    private List<Course> listCourseExclue= new ArrayList<Course>() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        lbListCourse.setText("");
        lbListCourse.setVisible(false);
        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
            this.school = SchoolManagingApplication.getMySchool();
            lbListCourse.setVisible(true);
            initializecb();
            chargeListe();
            initializeButtons();
            initializeSelection();
            lbId.setVisible(false);
        });
        lbListCourse.setVisible(false);
        initializecb();
        initializeTableView();
        initializeButtons();
        initializeSelection();
        lbId.setVisible(false);
    }

    private void initializecb() {
        //recupere liste des courses
        GluonObservableList<Course> listCourse = HttpRequests.getAllCourse(school.getId());
        listCourse.setOnSucceeded(connectStateEvent -> {
            this.cbExcluCourse.setItems(listCourse);
        });

        //formate la combobox
        Callback<ListView<Course>, ListCell<Course>> roomCellFactory =
                new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> l) {
                return new ListCell<Course>() {

                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getId()+" "+item.getName());
                        }
                    }
                } ;
            }
        };

        this.cbExcluCourse.setButtonCell(roomCellFactory.call(null));
        this.cbExcluCourse.setCellFactory(roomCellFactory);
    }


    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
            txtName.clear();
            txtCapacity.clear();
            lbListCourse.setText("");
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            ClassRoom myClassRoom = new ClassRoom();
            myClassRoom.setName(txtName.getText());
            myClassRoom.setCapacity(Integer.parseInt(txtCapacity.getText()));
            myClassRoom.setSchool(school);
            myClassRoom.setExcludedCourses(listCourseExclue);
            GluonObservableObject<ClassRoom> potentialConnected = HttpRequests.addClassRoom(myClassRoom);
            potentialConnected.setOnFailed(connectStateEvent -> {
                classRoomss.add(myClassRoom);
                chargeListe();
            });
            potentialConnected.setOnSucceeded(connectStateEvent -> {
                classRoomss.add(myClassRoom);
                chargeListe();
            });
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteCourse(id);
        });

        btnClearExcluCourse.setOnMouseClicked(mouseEvent -> {
            lbListCourse.setText("");
            listCourseExclue.clear();
        });

        btnAddExcluCourse.setOnMouseClicked(mouseEvent -> {
            Course cExclu = (Course) cbExcluCourse.valueProperty().getValue();
            listCourseExclue.add(cExclu);
            String stringC ="";
            for (Course c: listCourseExclue) {
                stringC+=c.getName()+", ";
            }
            lbListCourse.setText(stringC);
        });
    }
    private void initializeTableView() {
        TableColumn<ClassRoom, String> idRoomCol = new TableColumn<>("Identifiant");
        TableColumn<ClassRoom, String> nameRoomCol = new TableColumn<>("Nom");
        TableColumn<ClassRoom, String> capRommCol = new TableColumn<>("Capacit??");

        tbView.getColumns().addAll(idRoomCol,nameRoomCol,capRommCol);
        idRoomCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameRoomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        capRommCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        chargeListe();

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedClassRoom.setValue((ClassRoom) t1);
        });
    }

    private void chargeListe() {
        GluonObservableList<ClassRoom> gotList = HttpRequests.getAllClassRoom(school.getId());
        gotList.setOnSucceeded(connectStateEvent -> {
            this.classRoomss = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.classRoomss);
        });

    }

    private void initializeSelection() {
        this.selectedClassRoom.addListener((observableValue, classRoom, classRoom1)-> {

            if(classRoom1==null){

                lbId.setVisible(false);
                txtName.clear();
                txtCapacity.clear();
                lbListCourse.setVisible(true);
                String stringC ="";
                for (Course c: classRoom1.getExcludedCourses()) {
                    stringC+=c.getName()+", ";
                }
                lbListCourse.setText(stringC);

            }
            else{

            this.lbId.setText(String.valueOf(classRoom1.getId()));
            this.lbId.setVisible(true);
            this.txtName.setText(classRoom1.getName());
            this.txtCapacity.setText(String.valueOf(classRoom1.getCapacity()));
                lbListCourse.setVisible(true);
                String stringC ="";
                for (Course c: classRoom1.getExcludedCourses()) {
                    stringC+=c.getName()+", ";
                }
                lbListCourse.setText(stringC);
            }
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des salles  ");
    }
}
