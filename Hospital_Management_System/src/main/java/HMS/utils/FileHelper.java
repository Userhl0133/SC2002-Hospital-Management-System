package HMS.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            for (int i = 2; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");
                
                String id = values[0];
                String name = values[1];
                LocalDate dob = LocalDate.parse(values[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Gender gender = Gender.valueOf(values[3].toUpperCase());
                BloodType bloodType = BloodType.fromDisplayName(values[4].toUpperCase());
                String contactInfo = values[5];
                String password = values[6];
                
                // Set a default role, such as 'PATIENT'
                Role role = Role.PATIENT;

                // Create Patient object
                Patient patient = new Patient(id, password, gender, name, role, dob.atStartOfDay(), bloodType, contactInfo);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static List<Object> getStaffData(String filePath) {
        List<Object> staff = new ArrayList<>();
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (int i = 2; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");
                
                String id = values[0];
                String name = values[1];
                LocalDate dob = LocalDate.parse(values[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Gender gender = Gender.valueOf(values[3].toUpperCase());
                String contactInfo = values[4];
                String password = values[5];
                Role role = Role.valueOf(values[6].toUpperCase());

                switch (role) {
                    case DOCTOR:
                        String specialization = values[7];
                        Doctor doctor = new Doctor(id, password, gender, name, role, dob.atStartOfDay(), contactInfo, specialization);
                        staff.add(doctor);
                        break;
                    case ADMINISTRATOR:
                        Administrator administrator = new Administrator(id, password, gender, name, role, dob.atStartOfDay(), contactInfo);
                        staff.add(administrator);
                        break;
                    case PHARMACIST:
                        Pharmacist pharmacist = new Pharmacist(id, password, gender, name, role, dob.atStartOfDay(), contactInfo);
                        staff.add(pharmacist);
                        break;
                    default:
                        // Handle unknown role if necessary
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staff;
    }

    public static List<Medication> getMedicationsData(String filePath) {
        List<Medication> medications = new ArrayList<>();
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for (int i = 2; i < lines.size(); i++) {  // Skip header
                String line = lines.get(i);
                String[] values = line.split(",");
                
                String id = values[0];
                String name = values[1];
                String description = values[2];
                double price = Double.parseDouble(values[3]);
                int quantity = Integer.parseInt(values[4]);
                
                Medication medication = new Medication(id, name, description, price, quantity);
                medications.add(medication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
