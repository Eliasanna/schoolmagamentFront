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
public class ClassRoom {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private int capacity;

    private School school;

    private List<Course> excludedCourses=new ArrayList<>();

}
