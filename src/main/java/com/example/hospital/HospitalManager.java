package com.example.hospital;

public class HospitalManager {
    private static HospitalManager instance;

    private HospitalManager() {}

    public static HospitalManager getInstance() {
        if (instance == null) {
            instance = new HospitalManager();
        }
        return instance;
    }

    public void log(String msg) {
        System.out.println("[LOG]: " + msg);
    }
}
