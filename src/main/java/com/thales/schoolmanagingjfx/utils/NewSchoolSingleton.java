package com.thales.schoolmanagingjfx.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class NewSchoolSingleton {

    private static NewSchoolSingleton INSTANCE;

    private static BooleanProperty addSchool = new SimpleBooleanProperty();

    private  NewSchoolSingleton(){}

    public static NewSchoolSingleton getInstance(){
        if (INSTANCE ==null){
            INSTANCE = new NewSchoolSingleton();
        }
        return INSTANCE;
    }

    public boolean isAddSchool() {
        return addSchool.get();
    }

    public BooleanProperty addSchoolProperty() {
        return addSchool;
    }

    public void setAddSchool(boolean addSchool) {
        NewSchoolSingleton.addSchool.set(addSchool);
    }
}
