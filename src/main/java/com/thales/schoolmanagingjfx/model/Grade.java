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
public class Grade {

    private int id;

    @NonNull
    private String name;

    @NonNull
    private String section;

    private List<Lesson> lessons=new ArrayList<>();

    private Teacher mainTeacher;

    School school;
}
