package it2e.appointments;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Appointments {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void appointmentConfig(){
        int option;
        do {
            try {
                System.out.println("\n\t--- Appointments Menu ---\n");
                System.out.println("1. Schedule Appointment");
                System.out.println("2. View Appointments");
                System.out.println("3. Edit Appointment");
                System.out.println("4. Delete Appointment");
                System.out.println("5. Exit");
                
                System.out.print("\nChoose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 

                switch (option) {
                    case 1:
                        System.out.println("\n   --- SCHEDULE APPOINTMENT ---\n");
                        scheduleAppointment();
                        break;
                    case 2:
                        System.out.println("\n\t\t\t\t\t\t\t\t   --- APPOINTMENTS LIST ---");
                        
                        viewAppointments();
                        break;
                    case 3:
                        System.out.println("\n   --- EDITING AN APPOINTMENT ---\n");
                        editAppointment();
                        break;
                    case 4:
                        System.out.println("\n   --- DELETING AN APPOINTMENT ---\n");
                        deleteAppointment();
                        break;
                    case 5:
                        System.out.println("Exiting Appointments Menu..");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    public void scheduleAppointment() {
        System.out.println("Enter Appointment Details:");
        
        Patients pat = new Patients();
        pat.viewPatients();
        int p_id;
        do{
            System.out.print("\nPatient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));
        scan.nextLine();
        
        System.out.print("Appointment Type: ");
        String type = scan.nextLine();
        
        System.out.print("Doctor Name: ");
        String doc = scan.nextLine();
        
        System.out.print("Appointment Date: ");
        String date = scan.nextLine();
        
        System.out.print("Appointment Status: ");
        String stats = scan.nextLine();
        
        String sql = "INSERT INTO appointments (patient_id, a_type, p_doctor, a_date, a_status) VALUES (?, ?, ?, ?, ?)";       
        conf.addRecord(sql, p_id, type, doc, date, stats);
    }

    public void viewAppointments() {
        String query = "SELECT app.id, pat.name, app.a_type, app.p_doctor, app.a_date, app.a_status"
                              + " FROM appointments app"
                              + " JOIN patients pat ON app.patient_id = pat.id";
        
        String[] Headers = {"ID", "Patient Name", "Appointment Type", "Doctor", "Appointment Date", "Appointment Status"};
        String[] Columns = {"id", "name", "a_type", "p_doctor", "a_date", "a_status"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void editAppointment() {
        int id;        
        boolean idExists;
        
        viewAppointments();
        
        do{
            System.out.print("Appointment ID you want to edit: ");
            id = scan.nextInt();
            
            idExists = conf.doesIDExist("appointments", id);
            if(!idExists){
                System.out.println("Appointment ID Doesn't Exist.\n");
            }
        }while(!idExists);
        
        System.out.println("Enter Appointment Details:");
        
        int p_id;
       /* do{
            System.out.print("\nNew Patient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));*/
        scan.nextLine();
        
        System.out.print("New Appointment Type: ");
        String type = scan.nextLine();
        
        System.out.print("New Doctor Name: ");
        String doc = scan.nextLine();
        
        System.out.print("New Appointment Date: ");
        String date = scan.nextLine();
        
        System.out.print("New Appointment Status: ");
        String stats = scan.nextLine();
        
        String sql = "UPDATE appointments SET a_type = ?, p_doctor = ?, a_date = ?, a_status = ? WHERE id = ?";       
        conf.updateRecord(sql, type, doc, date, stats, id); 
    }

    public void deleteAppointment() {
        viewAppointments();
        
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM appointments WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}

