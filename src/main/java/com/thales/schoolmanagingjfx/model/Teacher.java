package com.thales.schoolmanagingjfx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Teacher {

    private int id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private Date birthdate;

    private Grade grade;

    List<Lesson> lessons=new ArrayList<>();

    List<Course> courses=new ArrayList<>();

    School school;
}
