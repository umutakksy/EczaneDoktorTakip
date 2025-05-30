package com.example.hospital;

public class Medication {
    private String name;
    private String dosage;

    public Medication(String name, String dosage) {
        this.name = name;
        this.dosage = dosage;
    }

    public String toString() {
        return name + " - " + dosage;
    }
}
