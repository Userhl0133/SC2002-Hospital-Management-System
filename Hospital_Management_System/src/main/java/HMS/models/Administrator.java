package HMS.models;

import java.util.Scanner;

import static HMS.MainApp.administrators;
import static HMS.MainApp.doctors;
import static HMS.MainApp.pharmacists;
import HMS.enums.Gender;
import HMS.enums.Role;
public class Administrator extends User{

    private int age;

    // Constructor
    public Administrator(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    // Methods
    public void viewStaff() {
        // Implementation for displaying the list of staff members
        for (Administrator administrator : administrators){
            System.out.println(administrator);
        }
        for (Doctor doctor : doctors){
            System.out.println(doctor);
        }
        for (Pharmacist pharmacist : pharmacists){
            System.out.println(pharmacist);
        }
    }

    public void addStaff(String userId, String password, Gender gender, String name, Role role, int age) {
        // Implementation for adding a staff member
        if (role == Role.DOCTOR) {
            Doctor doctor = new Doctor(userId, password, gender, name, role, age);
            doctors.add(doctor);
        }
        else if (role == Role.ADMINISTRATOR) {
            Administrator administrator = new Administrator(userId, password, gender, name, role, age);
            administrators.add(administrator);
        }
        else if (role == Role.PHARMACIST) {
            Pharmacist pharmacist = new Pharmacist(userId, password, gender, name, role, age);
            pharmacists.add(pharmacist);
        }
    }

    public void updateStaff(User staff) {
        // Implementation for updating a staff member's details

    }

    public void removeStaff() {
        // Implementation for removing a staff member
    }

    public void viewAppointments() {
        // Implementation for viewing all appointments
    }

    public void viewInventory() {
        // Implementation for viewing inventory details
    }

    public void updateMedicationStock() {
        // Implementation for updating medication stock levels
    }

    public void updateLowStockLevel() {
        // Implementation for updating low stock level threshold
    }

    public void approveReplenishmentRequest() {
        // Implementation for approving a replenishment request
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 9) {
            System.out.println("============================");
            System.out.println("-----Administrator Menu-----");
            System.out.println("============================");
            System.out.println("1.View Staff");
            System.out.println("2.Add Staff");
            System.out.println("3.Update Staff");
            System.out.println("4.Remove Staff");
            System.out.println("5.View Appointments Details");
            System.out.println("6.View Medication Inventory");
            System.out.println("7.Update Medication stock");
            System.out.println("8.Approve Replenishment Request");
            System.out.println("9.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            switch(choice) {
                case 1 :
                    // View Staff
                    System.out.println("List of Staff:");
                    viewStaff();
                    break;

                case 2 :
                    // Add Staff
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Gender (Male/Female): ");
                    Gender gender = null;
                    while (gender == null) {
                        try {
                            gender = Gender.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid gender. Please enter Male or Female: ");
                        }
                    }

                    System.out.print("Enter Role (Administrator/Doctor/Pharmacist): ");
                    Role role = null;
                    while (role == null) {
                        try {
                            role = Role.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid role. Please enter Administrator, Doctor, or Pharmacist: ");
                        }
                    }

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();

                    addStaff(userId, password, gender, name, role, age);
                    break;

                case 3 :
                    // Update Staff
                    break;

                case 4 :
                    // Remove Staff
                    break;

                case 5 :
                    // View Appointments Details
                    break;

                case 6 :
                    // View Medication Inventory
                    break;

                case 7 :
                    // Update Medication stock
                    break;

                case 8 :
                    // Approve Replenishment Request
                    break;

                case 9 :
                    // Logout
                    System.out.println("Logging out");
                    break;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }
}
