package com.example.fitnessapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Exercise{

    private String name;
    private Long repetition;
    private double weight;

    public Exercise(String name, Long repetition, double weight) {
        this.name = name;
        this.repetition = repetition;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRepetition() {
        return repetition;
    }

    public void setRepetition(Long repetition) {
        this.repetition = repetition;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
