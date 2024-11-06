package HMS.models;

import java.util.Scanner;

public class Pharamacist extends User {


    public void showMenu() {
        int choice = 0;
        System.out.println("-----Pharmacist Menu-----");
        System.out.println("1.View Appointment Outcome Record");
        System.out.println("2.Update Prescription Status");
        System.out.println("3.View Medication Inventory");
        System.out.println("4.Submit Replenishment Request");
        System.out.println("5.Logout");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        while(choice != 9) {
            choice = sc.nextInt();
            switch(choice) {
                case 1:
                    // View Appointment Outcome Record
                    break;

                case 2:
                    // Update Prescription Status
                    break;

                case 3:
                    // View Available Appointment Slots
                    break;

                case 4:
                    // Submit Replenishment Request
                    break;

                case 5:
                    // Logout
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
