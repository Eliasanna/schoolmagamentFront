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
public class School {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private String type;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String logo;

    private Address address;

    private List<ClassRoom> classRooms=new ArrayList<>();

    List<Teacher> teachers=new ArrayList<>();

    List<Grade> grades=new ArrayList<>();

    List<Course> courses=new ArrayList<>();

}
