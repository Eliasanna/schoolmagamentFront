package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.Course;
import com.thales.schoolmanagingjfx.model.Grade;
import com.thales.schoolmanagingjfx.model.School;
import com.thales.schoolmanagingjfx.model.Teacher;
import com.thales.schoolmanagingjfx.utils.HttpRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionGradeController implements Initializable  {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public Label lbId;
    @FXML
    public TextField tbName;
    @FXML
    public TextField tbSection;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;
    @FXML
    public ComboBox cbMainTeacher;
    private School school = SchoolManagingApplication.getMySchool();
    private ObjectProperty<Grade> selectedGrade = new SimpleObjectProperty<Grade>();
    private ObservableList<Grade> grades;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeButtons();
        initializeTableView();
        initializeSelection();
        initializeCb();

        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
            this.school = SchoolManagingApplication.getMySchool();
            initializeButtons();
            chargeListe();
            initializeSelection();
            initializeCb();
        });
    }

    private void initializeCb() {
        GluonObservableList<Teacher> listTeacher = HttpRequests.getAllTeacher(school.getId());
        listTeacher.setOnSucceeded(connectStateEvent -> {
            this.cbMainTeacher.setItems(listTeacher);
            Teacher gVide = new Teacher();
            listTeacher.add(gVide);
        });

        Callback<ListView<Teacher>, ListCell<Teacher>> roomCellFactory = new Callback<ListView<Teacher>, ListCell<Teacher>>() {
            @Override
            public ListCell<Teacher> call(ListView<Teacher> l) {
                return new ListCell<Teacher>() {
                    @Override
                    protected void updateItem(Teacher item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getId()+" "+item.getFirstName()+" "+item.getLastName());
                }}};
            }
        };
        this.cbMainTeacher.setButtonCell(roomCellFactory.call(null));
        this.cbMainTeacher.setCellFactory(roomCellFactory);
    }

    private void initializeTableView() {
        TableColumn<Grade, String> idGradeCol = new TableColumn<>("Identifiant");
        TableColumn<Grade, String> nameGradeCol = new TableColumn<>("Nom");
        TableColumn<Grade, String> sectionGradeCol = new TableColumn<>("Section");
        TableColumn<Grade,String> profGradeCol = new TableColumn<>("Prof principal");

        tbView.getColumns().addAll(idGradeCol,nameGradeCol,sectionGradeCol,profGradeCol);

        idGradeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameGradeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionGradeCol.setCellValueFactory(new PropertyValueFactory<>("section"));

        profGradeCol.setCellValueFactory(teacherStringCellDataFeatures -> {
            if(teacherStringCellDataFeatures.getValue().getMainTeacher()==null){
                return new SimpleStringProperty("");
            }
            String teacherName = teacherStringCellDataFeatures.getValue().getMainTeacher().getFirstName()
                    +" "+teacherStringCellDataFeatures.getValue().getMainTeacher().getLastName() ;
            return new SimpleStringProperty(teacherName);
        });

        chargeListe();

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedGrade.setValue((Grade) t1);
        });
    }

    private void chargeListe() {
        GluonObservableList<Grade> gotList = HttpRequests.getAllGrade(school.getId());
        gotList.setOnSucceeded(connectStateEvent -> {
            this.grades = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.grades);
        });
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
            tbName.clear();
            tbSection.clear();
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            Grade myGrade= new Grade();
            myGrade.setName(tbName.getText());
            myGrade.setSection(tbSection.getText());
            myGrade.setSchool(school);
            myGrade.setMainTeacher((Teacher) cbMainTeacher.valueProperty().getValue());

            GluonObservableObject<Grade> potentialConnected = HttpRequests.addGrade(myGrade);
            potentialConnected.setOnFailed(connectStateEvent -> {
                grades.add(myGrade);
                chargeListe();
            });
            potentialConnected.setOnSucceeded(connectStateEvent -> {
                grades.add(myGrade);
                chargeListe();
            });
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteGrade(id);
            chargeListe();
        });
    }

    private void initializeSelection() {
        this.selectedGrade.addListener((observableValue, grade, grade1)-> {
            if(grade1==null){
                lbId.setText("");
                lbId.setVisible(false);
                tbName.clear();
                tbSection.clear();
            }
            else {
            this.lbId.setText(String.valueOf(grade1.getId()));
            lbId.setVisible(true);
            this.tbName.setText(grade1.getName());
            this.tbSection.setText(grade1.getSection());
            this.cbMainTeacher.setValue(grade1.getMainTeacher());
            }
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des classes  ");
    }

}
