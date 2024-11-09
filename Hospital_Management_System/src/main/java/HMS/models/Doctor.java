package HMS.models;


import HMS.enums.*;

import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import HMS.enums.Gender;
import HMS.enums.Role;


import HMS.MainApp;

public class Doctor extends User {

    // Attributes
    // availability hashmap contains time slots where the doctor is unavailable, in 1 hour blocks
    // Key: Date in (DDMMYYYY) format, Value: Timeslot from int 1-9 (0900-1700)
    private Map<Integer, List<Integer>> availability;
    private int age;

    public Doctor() {
    }

    // Constructor
    public Doctor(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.availability = new HashMap<>();
        this.age = age;
    }

    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : MainApp.doctors) {
            if (doctor.getUserId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    public Map<Integer, List<Integer>> getAvailability() {
        return availability;
    }

    // Methods
    public void viewPersonalSchedule() {
        // Implementation for viewing the personal schedule

    }

    public void updatePatientParticular() {
        // Implementation for updating patient particulars

    }

    public void viewUpcomingAppointment() {
        // Implementation for viewing upcoming appointments
    }

    public void updateAppointmentOutcomeRecord() {
        // Implementation for updating appointment outcome records
    }

    public void viewAppointmentOutcomeRecord() {
        // Implementation for viewing appointment outcome records
    }

    public void createAppointmentOutcomeRecord() {
        // Implementation for creating an appointment outcome record
    }

    public void cancelAppointment() {
        // Implementation for canceling an appointment
    }

    // Returns -1 if time slot removed, 1 if added
    public int setAvailability(int date, int time) {
        // If timing exists in availability list, remove the time slot
        if (this.availability.containsKey(date)) {
            if (this.availability.get(date).contains(time)) {
                this.availability.get(date).remove(Integer.valueOf(time));
                return -1;
            } else {
                this.availability.get(date).add(time);
                return 1;
            }
        } // If timing does not exist, add time slot
        else {
            List<Integer> timeSlots = new ArrayList<>();
            timeSlots.add(time);
            this.availability.put(date, timeSlots);
            return 1;
        }
    }

    public void updateAppointment() {
        // Implementation for updating an appointment
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 8) {
            System.out.println("=====================");
            System.out.println("-----Doctor Menu-----");
            System.out.println("=====================");
            System.out.println("1.View Patient Medical Records");
            System.out.println("2.Update Patient Medical Records");
            System.out.println("3.View Personal Schedule");
            System.out.println("4.Set Availability for Appointments");
            System.out.println("5.Accept or Decline Appointment Requests");
            System.out.println("6.View Upcoming Appointments");
            System.out.println("7.Record Appointment Outcome");
            System.out.println("8.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // View Patient Medical Records
                    break;

                case 2:
                    // Update Patient Medical Records
                    break;

                case 3:
                    // View Personal Schedule
                    break;

                case 4:
                    System.out.println("Set unavailable timeslot");
                    System.out.println("Enter date (DDMMYYYY): ");
                    int date = sc.nextInt();

                    // Print timeslots
                    int counter = 1;
                    for (int timeslot = 900; timeslot <= 1700; timeslot += 100) {
                        System.out.printf("[%d] %04d", counter, timeslot);
                        if (this.availability.containsKey(date) && this.availability.get(date).contains(counter)) {
                            System.out.print(" (unavailable)");
                        }
                        System.out.println();
                        counter++;
                    }

                    System.out.println("Enter a timeslot number: ");
                    int time = sc.nextInt();

                    if (time < 1 || time > 9) {
                        System.out.println("Invalid timeslot. Please try again.");
                        break;
                    }

                    if (setAvailability(date, time) == 1) {
                        System.out.println(date + " " + (time + 8) * 100 + " set as unavailable");
                    } else {
                        System.out.println(date + " " + (time + 8) * 100 + " set as available");
                    }
                    break;

                case 5:
                    // Accept or Decline Appointment Requests
                    break;

                case 6:
                    // View Upcoming Appointments

                    break;

                case 7:
                    // Record Appointment Outcome

                    break;

                case 8:
                    // Logout
                    System.out.println("Logging out");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
