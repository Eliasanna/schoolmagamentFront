package com.thales.schoolmanagingjfx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Course {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private String color;

    private List<ClassRoom> excludedClassRooms=new ArrayList<>();

    List<Teacher> teachers =new ArrayList<>();

    School school;

}
