package com.thales.schoolmanagingjfx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Lesson {

    private int id;

    @NonNull
    private Date startHour;

    @NonNull
    private Date endHour;

    private Course course;

    private Grade grade;

    private Teacher teacher;
}
