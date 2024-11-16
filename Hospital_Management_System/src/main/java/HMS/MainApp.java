package HMS;

import java.util.ArrayList;
import java.util.List;
import HMS.models.*;
import HMS.service.IFileService;
import HMS.service.IUserService;
import HMS.utils.FileHelper;
import HMS.utils.UserHelper;

public class MainApp {
    private final IFileService fileService;
    private final IUserService userService;

    public static List<Patient> patients = new ArrayList<>();
    public static List<Doctor> doctors = new ArrayList<>();
    public static List<Pharmacist> pharmacists = new ArrayList<>();
    public static List<Administrator> administrators = new ArrayList<>();
    public static List<Medication> medications = new ArrayList<>();
    public static List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    // Constructor to inject dependencies
    public MainApp(IFileService fileService, IUserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    public void run() {
        initialiseData();

        // Menu Loop
        while (true) {
            System.out.println("====================================");
            System.out.println("-----Hospital Management System-----");
            System.out.println("====================================");
            System.out.println("Please log in");

            User validatedUser = User.login(patients, doctors, pharmacists, administrators, userService);
            if (validatedUser != null) {
                validatedUser.showMenu();
                storeData();
            } else {
                System.out.println("Invalid login. Please try again.");
            }
        }
    }

    // Method to initialize data from CSV files
    public void initialiseData() {
        // Load data from files
        patients = fileService.getPatientsData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Patient_Data.csv");
        List<Object> staff = fileService.getStaffData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Staff_Data.csv");
        medications = fileService.getMedicationsData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Medicine_Data.csv");

        // Separate staff into their respective categories
        for (Object staffMember : staff) {
            if (staffMember instanceof Doctor doctor) {
                doctors.add(doctor);
            } else if (staffMember instanceof Pharmacist pharmacist) {
                pharmacists.add(pharmacist);
            } else if (staffMember instanceof Administrator administrator) {
                administrators.add(administrator);
            }
        }
        fileService.getDoctorAvailability(System.getProperty("user.dir") + "/src/main/java/HMS/data/Doctor_Availability.csv");
    }

    // Method to store data back to CSV files
    public void storeData() {
        fileService.writePatientsData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Patient_Data.csv");
        fileService.writeStaffData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Staff_Data.csv");
        fileService.writeMedicationData(System.getProperty("user.dir") + "/src/main/java/HMS/data/Medicine_Data.csv");
        fileService.writeDoctorAvailability(System.getProperty("user.dir") + "/src/main/java/HMS/data/Doctor_Availability.csv");
    }

    public static void main(String[] args) {
        // Injecting dependencies
        IFileService fileService = new FileHelper();
        IUserService userService = UserHelper.getInstance();

        // Creating and running the application
        MainApp app = new MainApp(fileService, userService);
        app.run();
    }
}
