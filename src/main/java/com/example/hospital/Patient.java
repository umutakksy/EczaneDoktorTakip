package com.example.hospital;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String complaint;
    private List<String> medications;
    private List<String> tests;
    private LocalDateTime createdAt;

    public Patient(String name, String complaint) {
        this.name = name;
        this.complaint = complaint;
        this.medications = new ArrayList<>();
        this.tests = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getComplaint() {
        return complaint;
    }

    public void addMedication(String medication, String dosage) {
        medications.add(medication + " (" + dosage + ")");
    }

    public void addTest(String test) {
        tests.add(test);
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();

        info.append("👤 HASTA ADI: ").append(name).append("\n");
        info.append("📅 KAYIT TARİHİ: ").append(createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).append("\n");
        info.append("💬 ŞİKAYET: ").append(complaint).append("\n\n");

        info.append("💊 YAZILAN İLAÇLAR:\n");
        if (medications.isEmpty()) {
            info.append("   • Henüz ilaç yazılmamış\n");
        } else {
            for (int i = 0; i < medications.size(); i++) {
                info.append("   ").append(i + 1).append(". ").append(medications.get(i)).append("\n");
            }
        }

        info.append("\n🔬 İSTENEN TETKİKLER:\n");
        if (tests.isEmpty()) {
            info.append("   • Henüz tetkik istenmemiş\n");
        } else {
            for (int i = 0; i < tests.size(); i++) {
                info.append("   ").append(i + 1).append(". ").append(tests.get(i)).append("\n");
            }
        }

        return info.toString();
    }
}