package com.example.hospital;

public class HospitalFacade {

    public Patient createPatient(String name, String complaint) {
        // Basit validasyon
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hasta adı boş olamaz!");
        }

        if (complaint == null || complaint.trim().isEmpty()) {
            throw new IllegalArgumentException("Hasta şikayeti boş olamaz!");
        }

        return new Patient(name.trim(), complaint.trim());
    }

    public void prescribeMedication(Patient patient, String medicationName, String dosage) {
        if (patient == null) {
            throw new IllegalArgumentException("Hasta bilgisi bulunamadı!");
        }

        if (medicationName == null || medicationName.trim().isEmpty()) {
            throw new IllegalArgumentException("İlaç adı boş olamaz!");
        }

        if (dosage == null || dosage.trim().isEmpty()) {
            throw new IllegalArgumentException("İlaç dozu boş olamaz!");
        }

        patient.addMedication(medicationName.trim(), dosage.trim());
    }

    public void requestTest(Patient patient, String testName) {
        if (patient == null) {
            throw new IllegalArgumentException("Hasta bilgisi bulunamadı!");
        }

        if (testName == null || testName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tetkik adı boş olamaz!");
        }

        patient.addTest(testName.trim());
    }
}