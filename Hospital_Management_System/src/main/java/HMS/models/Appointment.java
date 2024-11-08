package HMS.models;

import java.time.LocalDateTime;

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
    @Override
    public String toString() {
        return String.format("Appointment ID: %d, Patient ID: %s, Doctor ID: %s, Status: %s, Date & Time: %s",
                appointmentID, patientID, doctorID, appointmentStatus, dateTime);
    }
}
