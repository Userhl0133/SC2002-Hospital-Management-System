package HMS.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static HMS.MainApp.patients;
import HMS.enums.AppointmentStatus;

public class Appointment {

    private static int nextAppointmentID = 1;
    private int appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private AppointmentStatus appointmentStatus;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

    // Constructor
    public Appointment(String patientID, String doctorID, LocalDateTime dateTime, AppointmentStatus appointmentStatus, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentID = nextAppointmentID++; // Assume system data does not persist.
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    // Getters and Setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public AppointmentOutcomeRecord getAppointmentOutcomeRecord() {
        return appointmentOutcomeRecord;
    }

    public void setAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    public LocalDateTime getAppointmentDateTime() {
        return this.dateTime;
    }

    public static ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> result = new ArrayList<>();
        for(Patient patient : patients){
            result.addAll(patient.getAppointments());
        }
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = dateTime.format(formatter);
        Doctor doctor = new Doctor();
        doctor = doctor.getDoctorById(doctorID);
        Patient patient = new Patient();
        patient = patient.getPatientById(patientID);
        return String.format("Appointment ID: %d\nDoctor: %s %s\nPatient: %s %s\nStatus: %s\nDate & Time: %s\n\nAppointment Outcome: %s",
                appointmentID, doctorID, doctor.getName() , patientID, patient.getName(), appointmentStatus, formattedDateTime, appointmentOutcomeRecord);
    }
}