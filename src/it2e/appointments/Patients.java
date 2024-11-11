package it2e.appointments;

import java.util.Scanner;

public class Patients {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void patientConfig() {
        int option;
        do {
            System.out.println("\n\t Patients \t");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Edit Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Exit");
            
            System.out.print("\nChoose an option: ");
            option = scan.nextInt();
            scan.nextLine(); 

            switch (option) {
                case 1:
                    System.out.println("\n   'ADDING NEW PATIENT' \n");
                    addPatient();
                    break;
                case 2:
                    System.out.println("\n\t\t\t   'PATIENTS LIST'");
                    viewPatients(); 
                    break;
                case 3:
                    editPatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    System.out.println("Exiting Patients Menu.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addPatient() {
        System.out.println("Enter Appointment Details:");
        
        System.out.print("\nPatient Name: ");
        String name = scan.nextLine();
        
        System.out.print("Email: ");
        String email = scan.nextLine();
        
        String sql = "INSERT INTO patients (name, email) VALUES (?, ?)";       
        conf.addRecord(sql, name, email);
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        String[] Headers = {"ID", "Patient Name", "Email"};
        String[] Columns = {"id", "name", "email"};
        
        conf.viewRecords(query, Headers, Columns);
     }

    private void editPatient() {
        int p_id;
        
        viewPatients(); 
        
        do{
            System.out.print("\nEnter Patient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));
        scan.nextLine();
        
        System.out.println("Enter New Patient Details:");
        
        System.out.print("New Patient Name: ");
        String name = scan.nextLine();
        
        System.out.print("New Email: ");
        String email = scan.nextLine();
        
        String sql = "UPDATE patients SET name = ?, email = ? WHERE id = ?";
        conf.updateRecord(sql, name, email, p_id);
    }

    private void deletePatient() {
        viewPatients(); 
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM patients WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
