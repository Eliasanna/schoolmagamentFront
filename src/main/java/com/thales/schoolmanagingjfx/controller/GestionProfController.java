package com.thales.schoolmanagingjfx.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.thales.schoolmanagingjfx.SchoolManagingApplication;
import com.thales.schoolmanagingjfx.model.*;
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
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionProfController implements Initializable {
    @FXML
    public HBox enTete;
    @FXML
    public TableView tbView;
    @FXML
    public Label lbId;
    @FXML
    public TextField tbFName;
    @FXML
    public TextField txtLName;

    @FXML
    public ComboBox cbCourse1;
    @FXML
    public ComboBox cbCourse2;
    @FXML
    public ComboBox cbCourse3;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSup;
    @FXML
    public TextField txtDate;

    private School school = SchoolManagingApplication.getMySchool();
    private ObjectProperty<Teacher> selectedTeacher = new SimpleObjectProperty<Teacher>();

    private ObservableList<Teacher> teachers;

    private List<Course> listCourseTeacher = new ArrayList<Course>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeTableView();
        initializeButtons();
        initializecb1();

        initializeSelection();
        SchoolManagingApplication.mySchoolProperty().addListener((observableValue, school, t1) -> {
            this.school = SchoolManagingApplication.getMySchool();
            chargeList();
            initializeButtons();
            initializecb1();

            initializeSelection();
        });

    }
    private void initializeSelection() {
        this.selectedTeacher.addListener((observableValue, teacher, teacher1)-> {
            if(teacher1==null){
                lbId.setVisible(false);
                lbId.setText("");
                tbFName.clear();
                txtLName.clear();
                txtDate.clear();
            }
            else {
                this.lbId.setText(String.valueOf(teacher1.getId()));
                lbId.setVisible(true);
                this.tbFName.setText(teacher1.getFirstName());
                this.txtLName.setText(teacher1.getLastName());
                this.txtDate.setText(teacher1.getBirthdate());
                this.listCourseTeacher=teacher1.getCourses();
                    System.out.println(listCourseTeacher);
                    System.out.println(listCourseTeacher.size());

                    if (listCourseTeacher.size()==1){
                        this.cbCourse1.setValue(listCourseTeacher.get(0));
                    } else if (listCourseTeacher.size()==1) {
                        this.cbCourse1.setValue(listCourseTeacher.get(0));
                        this.cbCourse2.setValue(listCourseTeacher.get(1));
                    }
                    else{
                        this.cbCourse1.setValue(listCourseTeacher.get(0));
                        this.cbCourse2.setValue(listCourseTeacher.get(1));
                        this.cbCourse3.setValue(listCourseTeacher.get(2));
                    }
                }
        });
    }

    private void initializeButtons() {
        btnClear.setOnMouseClicked(mouseEvent -> {
            lbId.setVisible(false);
            lbId.setText("");
            tbFName.clear();
            txtLName.clear();
            txtDate.clear();
        });

        btnAdd.setOnMouseClicked(mouseEvent -> {
            Teacher myTeacher = new Teacher();
            myTeacher.setFirstName(tbFName.getText());
            myTeacher.setLastName(txtLName.getText());
            myTeacher.setBirthdate(txtDate.getText());
            listCourseTeacher.clear();
            Course c1 = (Course) cbCourse1.valueProperty().getValue();
            Course c2 = (Course) cbCourse2.valueProperty().getValue();
            Course c3 = (Course) cbCourse3.valueProperty().getValue();
            if(c1==null){}
            else{listCourseTeacher.add(c1);}
            if(c2==null){}
            else{listCourseTeacher.add(c2);}
            if(c2==null){}
            else{listCourseTeacher.add(c3);}
            if(listCourseTeacher==null){}
            else{myTeacher.setCourses(listCourseTeacher);}
            myTeacher.setSchool(school);

            GluonObservableObject<Teacher> potentialConnected = HttpRequests.addTeacher(myTeacher);

            potentialConnected.setOnFailed(connectStateEvent -> {
                teachers.add(myTeacher);
                chargeList();
            });
            potentialConnected.setOnSucceeded(connectStateEvent -> {
                teachers.add(myTeacher);
                chargeList();
            });
        });

        btnSup.setOnMouseClicked(mouseEvent -> {
            String id = lbId.getText();
            HttpRequests.deleteTeacher(id);
            chargeList();
        });

    }

    private void initializecb1() {
        GluonObservableList<Course> listCourse = HttpRequests.getAllCourse(school.getId());
        listCourse.setOnSucceeded(connectStateEvent -> {
            this.cbCourse1.setItems(listCourse);
            this.cbCourse2.setItems(listCourse);
            this.cbCourse3.setItems(listCourse);
            Course courseVide = new Course();
            listCourse.add(courseVide);
        });

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
                        };
                    }
                };

        this.cbCourse1.setButtonCell(roomCellFactory.call(null));
        this.cbCourse1.setCellFactory(roomCellFactory);

        this.cbCourse2.setButtonCell(roomCellFactory.call(null));
        this.cbCourse2.setCellFactory(roomCellFactory);

        this.cbCourse3.setButtonCell(roomCellFactory.call(null));
        this.cbCourse3.setCellFactory(roomCellFactory);
    }



    private void initializeTableView() {
        //TableColumn<Teacher, String> idProfCol = new TableColumn<>("Identifiant");
        TableColumn<Teacher, String> fnameProfCol = new TableColumn<>("Nom");
        TableColumn<Teacher, String> lnameProfCol = new TableColumn<>("Pr??nom");
        TableColumn<Teacher, String> dateProfCol = new TableColumn<>("Date de naissance");
        TableColumn<Teacher, String> profMatCol = new TableColumn<>("Mati??res");

        tbView.getColumns().addAll(fnameProfCol,lnameProfCol,dateProfCol,profMatCol);

        //idProfCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameProfCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lnameProfCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        dateProfCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        //profMatCol.setCellValueFactory(new PropertyValueFactory<>("courses"));

        profMatCol.setCellValueFactory(coureStringCellDataFeatures -> {
            if(coureStringCellDataFeatures.getValue().getCourses()==null){
                return new SimpleStringProperty("");
            }
            String liste="";
            for (Course c : coureStringCellDataFeatures.getValue().getCourses()) {
                liste = liste+(c.getName()+ " ");
            }
            return new SimpleStringProperty(liste);
        });

        chargeList();

        tbView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedTeacher.setValue((Teacher) t1);
        });
    }

    private void chargeList() {
        GluonObservableList<Teacher> gotList = HttpRequests.getAllTeacher(school.getId());
        gotList.setOnSucceeded(connectStateEvent -> {
            this.teachers = FXCollections.observableArrayList(gotList);
            tbView.setItems(this.teachers);

        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(1);
        title.setText("  Gestion des professeurs  ");
    }
}
