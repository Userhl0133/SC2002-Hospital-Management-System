package HMS.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import HMS.models.Patient;
import HMS.models.Doctor;
import HMS.models.Administrator;
import HMS.models.Pharmacist;
import HMS.models.Medication;

import HMS.enums.Gender;
import HMS.enums.BloodType;
import HMS.enums.Role;

public class FileHelper {

    public static List<Patient> getPatientsData(String filePath) {
        List<Patient> patients = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");

                String id = values[0];
                String name = values[1];
                LocalDate dob = LocalDate.parse(values[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Gender gender = Gender.valueOf(values[3].toUpperCase());
                BloodType bloodType = BloodType.fromDisplayName(values[4].toUpperCase());
                String contactInfo = values[5];
                String password = values[6].trim();;

                System.out.println("DEBUG: Reading password for user " + name + ": " + password);

                // Set a default role, such as 'PATIENT'
                Role role = Role.PATIENT;

                // Create Patient object
                Patient patient = new Patient(id, password, gender, name, role, dob, bloodType, contactInfo);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static void writePatientsData(String filePath, List<Patient> patients) {
        List<String> lines = new ArrayList<>();
        lines.add("ID,Name,DOB,Gender,BloodType,ContactInfo,Password"); // Header

        for (Patient patient : patients) {
            String line = String.join(",",
                    patient.getUserId(),
                    patient.getName(),
                    patient.getDOB().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    patient.getGender().toString(),
                    patient.getBloodType().getDisplayName(),
                    patient.getContactInfo(),
                    patient.getHashedPassword());
            lines.add(line);
        }

        try {
            Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Object> getStaffData(String filePath) {
        List<Object> staff = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");

                String id = values[0];
                String name = values[1];
                Gender gender = Gender.valueOf(values[3].toUpperCase());
                String password = values[5];
                int age = Integer.parseInt(values[4]);
                Role role = Role.valueOf(values[2].toUpperCase());

                switch (role) {
                    case DOCTOR:
                        Doctor doctor = new Doctor(id, password, gender, name, role, age);
                        staff.add(doctor);
                        break;
                    case ADMINISTRATOR:
                        Administrator administrator = new Administrator(id, password, gender, name, role, age);
                        staff.add(administrator);
                        break;
                    case PHARMACIST:
                        Pharmacist pharmacist = new Pharmacist(id, password, gender, name, role, age);
                        staff.add(pharmacist);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staff;
    }

    public static void writeStaffData(String filePath, List<Object> staff) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            // Write the header
            writer.write("id,name,role,gender,age,password");
            writer.newLine();

            // Write each staff member's details
            for (Object obj : staff) {
                if (obj instanceof Doctor) {
                    Doctor doctor = (Doctor) obj;
                    writer.write(String.format("%s,%s,%s,%s,%d,%s",
                            doctor.getUserId(),
                            doctor.getName(),
                            doctor.getRole().name(),
                            doctor.getGender().name(),
                            doctor.getAge(),
                            doctor.getHashedPassword()
                    ));
                } else if (obj instanceof Administrator) {
                    Administrator admin = (Administrator) obj;
                    writer.write(String.format("%s,%s,%s,%s,%d,%s",
                            admin.getUserId(),
                            admin.getName(),
                            admin.getRole().name(),
                            admin.getGender().name(),
                            admin.getAge(),
                            admin.getHashedPassword()
                    ));
                } else if (obj instanceof Pharmacist) {
                    Pharmacist pharmacist = (Pharmacist) obj;
                    writer.write(String.format("%s,%s,%s,%s,%d,%s",
                            pharmacist.getUserId(),
                            pharmacist.getName(),
                            pharmacist.getRole().name(),
                            pharmacist.getGender().name(),
                            pharmacist.getAge(),
                            pharmacist.getHashedPassword()
                    ));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Medication> getMedicationsData(String filePath) {
        List<Medication> medications = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");

                int id = i - 1;
                String name = values[0];
                int initialStock = Integer.parseInt(values[1]);
                int lowStockLevel = Integer.parseInt(values[2]);

                Medication medication = new Medication(id, initialStock, lowStockLevel, name);
                medications.add(medication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medications;
    }

    public static void writeMedicationData(String filePath, List<Medication> medications) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            // Write the header
            writer.write("name,initialStock,lowStockLevel");
            writer.newLine();

            // Write each medication's details
            for (Medication medication : medications) {
                writer.write(String.format("%s,%d,%d",
                        medication.getMedicationName(),
                        medication.getStockLevel(),
                        medication.getLowStockLevel()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getDoctorAvailability(String filePath, List<Doctor> doctors) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line;
            reader.readLine(); // Skip the header

            // Read each line of the CSV file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Extract doctor ID and date
                String doctorId = values[0];
                int dateKey = Integer.parseInt(values[1]);

                // Find the doctor in the existing list using the doctorId
                Doctor doctor = new Doctor().getDoctorById(doctorId);

                // If the doctor is found, update their availability
                if (doctor != null) {
                    // Parse available slots and update the doctor's availability
                    String[] slotStrings = values[2].split(";");
                    for (String slot : slotStrings) {
                        int timeSlot = Integer.parseInt(slot);
                        doctor.setAvailability(dateKey, timeSlot);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDoctorAvailability(String filePath, List<Doctor> doctors) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            // Write the header
            writer.write("doctorId,date,availableSlots");
            writer.newLine();

            // Iterate through each doctor and their availability map
            for (Doctor doctor : doctors) {
                Map<Integer, List<Integer>> availability = doctor.getAvailability();
                for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
                    int dateKey = entry.getKey();
                    List<Integer> slots = entry.getValue();

                    // Convert slots list to a comma-separated string
                    String slotsString = slots.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(";"));

                    // Write to the CSV
                    writer.write(String.format("%s,%d,%s",
                            doctor.getUserId(),
                            dateKey,
                            slotsString
                    ));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
