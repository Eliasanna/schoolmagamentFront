package com.thales.schoolmanagingjfx.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Address {

    private int id;
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private int streetNumber;
    @NonNull
    private String country;

    private School school;
}
