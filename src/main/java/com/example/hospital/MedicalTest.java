package com.example.hospital;

public class MedicalTest {
    private String name;
    private String result;

    public MedicalTest(String name) {
        this.name = name;
        this.result = "Bekleniyor...";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toString() {
        return name + " : " + result;
    }
}
