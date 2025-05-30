package com.example.hospital;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class MainController {

    @FXML private TextField tfName;
    @FXML private TextArea tfComplaint;
    @FXML private TextField tfMedName;
    @FXML private TextField tfDosage;
    @FXML private TextField tfTest;
    @FXML private TextArea taInfo;

    private HospitalFacade hospitalFacade = new HospitalFacade();
    private Patient currentPatient;

    @FXML
    public void initialize() {
        // Başlangıç mesajı
        taInfo.setText("🏥 Hastane Yönetim Sistemine Hoş Geldiniz!\n\n" +
                "💡 Sistem Kullanımı:\n" +
                "1. Öncelikle hasta bilgilerini girerek hasta oluşturun\n" +
                "2. İlaç reçetesi yazabilirsiniz\n" +
                "3. Tetkik isteyebilirsiniz\n" +
                "4. Hasta bilgilerini görüntüleyebilirsiniz\n\n" +
                "✨ Başlamak için hasta bilgilerini giriniz...");
    }

    @FXML
    public void onCreatePatient() {
        String name = tfName.getText().trim();
        String complaint = tfComplaint.getText().trim();

        if (name.isEmpty()) {
            showStyledAlert("⚠️ Eksik Bilgi", "Lütfen hasta adını giriniz.", Alert.AlertType.WARNING);
            tfName.requestFocus();
            return;
        }

        if (complaint.isEmpty()) {
            showStyledAlert("⚠️ Eksik Bilgi", "Lütfen hasta şikayetini giriniz.", Alert.AlertType.WARNING);
            tfComplaint.requestFocus();
            return;
        }

        try {
            currentPatient = hospitalFacade.createPatient(name, complaint);

            String successMessage = "✅ Hasta başarıyla oluşturuldu!\n\n" +
                    "👤 Hasta Adı: " + name + "\n" +
                    "💬 Şikayet: " + complaint + "\n\n" +
                    "🎯 Artık ilaç yazabilir ve tetkik isteyebilirsiniz.";

            taInfo.setText(successMessage);
            showSuccessAlert("✅ Başarılı", "Hasta başarıyla oluşturuldu: " + name);
            clearPatientInputs();

        } catch (Exception e) {
            showStyledAlert("❌ Hata", "Hasta oluşturulurken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onPrescribeMedication() {
        if (currentPatient == null) {
            showStyledAlert("⚠️ Uyarı", "Önce hasta oluşturmalısınız.", Alert.AlertType.WARNING);
            tfName.requestFocus();
            return;
        }

        String medName = tfMedName.getText().trim();
        String dosage = tfDosage.getText().trim();

        if (medName.isEmpty()) {
            showStyledAlert("⚠️ Eksik Bilgi", "Lütfen ilaç adını giriniz.", Alert.AlertType.WARNING);
            tfMedName.requestFocus();
            return;
        }

        if (dosage.isEmpty()) {
            showStyledAlert("⚠️ Eksik Bilgi", "Lütfen ilaç dozunu giriniz.", Alert.AlertType.WARNING);
            tfDosage.requestFocus();
            return;
        }

        try {
            hospitalFacade.prescribeMedication(currentPatient, medName, dosage);

            String successMessage = "💊 İlaç başarıyla reçete edildi!\n\n" +
                    "👤 Hasta: " + currentPatient.getName() + "\n" +
                    "💉 İlaç: " + medName + "\n" +
                    "📏 Doz: " + dosage;

            taInfo.setText(successMessage);
            showSuccessAlert("✅ Başarılı", "İlaç başarıyla yazıldı: " + medName);
            clearMedicationInputs();

        } catch (Exception e) {
            showStyledAlert("❌ Hata", "İlaç yazılırken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onRequestTest() {
        if (currentPatient == null) {
            showStyledAlert("⚠️ Uyarı", "Önce hasta oluşturmalısınız.", Alert.AlertType.WARNING);
            tfName.requestFocus();
            return;
        }

        String testName = tfTest.getText().trim();
        if (testName.isEmpty()) {
            showStyledAlert("⚠️ Eksik Bilgi", "Lütfen tetkik adını giriniz.", Alert.AlertType.WARNING);
            tfTest.requestFocus();
            return;
        }

        try {
            hospitalFacade.requestTest(currentPatient, testName);

            String successMessage = "🔬 Tetkik başarıyla istendi!\n\n" +
                    "👤 Hasta: " + currentPatient.getName() + "\n" +
                    "🧪 Tetkik: " + testName + "\n\n" +
                    "📋 Tetkik sonucu için laboratuvarı takip ediniz.";

            taInfo.setText(successMessage);
            showSuccessAlert("✅ Başarılı", "Tetkik başarıyla istendi: " + testName);
            clearTestInputs();

        } catch (Exception e) {
            showStyledAlert("❌ Hata", "Tetkik istenirken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onShowInfo() {
        if (currentPatient == null) {
            showStyledAlert("⚠️ Uyarı", "Önce hasta oluşturmalısınız.", Alert.AlertType.WARNING);
            tfName.requestFocus();
            return;
        }

        try {
            String patientInfo = "📋 HASTA BİLGİLERİ DETAYI\n" +
                    "=" + "=".repeat(50) + "\n\n" +
                    currentPatient.getInfo() + "\n\n" +
                    "📊 Rapor Tarihi: " + java.time.LocalDateTime.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            ) + "\n" +
                    "=" + "=".repeat(50);

            taInfo.setText(patientInfo);

        } catch (Exception e) {
            showStyledAlert("❌ Hata", "Hasta bilgileri gösterilirken bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showStyledAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Alert stilini ayarla
        alert.getDialogPane().setStyle(
                "-fx-background-color: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Segoe UI';"
        );

        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Başarı alert'i için özel stil
        alert.getDialogPane().setStyle(
                "-fx-background-color: #d4edda; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Segoe UI';"
        );

        // Otomatik kapanma
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> alert.close());
        delay.play();

        alert.show();
    }

    private void clearPatientInputs() {
        tfName.clear();
        tfComplaint.clear();
    }

    private void clearMedicationInputs() {
        tfMedName.clear();
        tfDosage.clear();
    }

    private void clearTestInputs() {
        tfTest.clear();
    }

    @FXML
    public void onClearForm() {
        clearAllInputs();
        currentPatient = null;
        taInfo.setText("🏥 Hastane Yönetim Sistemine Hoş Geldiniz!\n\n" +
                "💡 Sistem Kullanımı:\n" +
                "1. Öncelikle hasta bilgilerini girerek hasta oluşturun\n" +
                "2. İlaç reçetesi yazabilirsiniz\n" +
                "3. Tetkik isteyebilirsiniz\n" +
                "4. Hasta bilgilerini görüntüleyebilirsiniz\n\n" +
                "✨ Başlamak için hasta bilgilerini giriniz...");

        showSuccessAlert("🗑️ Temizlendi", "Form başarıyla temizlendi!");
    }

    private void clearAllInputs() {
        clearPatientInputs();
        clearMedicationInputs();
        clearTestInputs();
    }
}