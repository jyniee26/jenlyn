package it2e.appointments;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IT2EAppointments {
    static Config conf = new Config();
    
    static Scanner scan = new Scanner(System.in);
    static Patients patient = new Patients();
    static Appointments appointments = new Appointments();
    
    public static void main(String[] args) { 
        
        
        int choice;

        do {    
            try {
                System.out.println("\n  APPOINTMENT RECORDS SYSTEM \n");
                System.out.println("1. Patients");
                System.out.println("2. Appointments");
                System.out.println("3. Generate Reports");
                System.out.println("4. Exit");
                
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println("");

                switch (choice) {
                    case 1:  
                        patient.patientConfig();
                        break;
                    case 2:
                        appointments.appointmentConfig();
                        break;
                    case 3:
                        individualReports();
                        break;
                    case 4:                      
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 4);
    }
    
    static void individualReports(){
        
        System.out.println("\n\t\t\t  PATIENTS LIST");
        patient.viewPatients();
        
        int p_id;
        do{
            System.out.print("\nEnter Patient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n\t\t\t\t\t\t\t   - INDIVIDUAL REPORT -");
        
        System.out.println("\nPatient ID    : " + p_id);
        System.out.println("Patient Name  : " + conf.getDataFromID("patients", p_id, "name"));
        System.out.println("Email Contact : " + conf.getDataFromID("patients", p_id, "email"));
        System.out.println("");
        
        
        if (conf.isTableEmpty("appointments WHERE patient_id = " + p_id)) {
            System.out.println("Patient Has no Appointment History.");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("Appointment History: ");
            String sql = "SELECT appointments.id, appointments.a_type, appointments.p_doctor, appointments.a_date, appointments.a_status " +
                         "FROM appointments " +
                         "JOIN patients ON appointments.patient_id = patients.id " +
                         "WHERE appointments.patient_id = " + p_id;

            String[] Headers = {"ID", "Appointment Type", "Doctor", "Appointment Date", "Appointment Status"};
            String[] Columns = {"id", "a_type", "p_doctor", "a_date", "a_status"};

            conf.viewRecords(sql, Headers, Columns);
            System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
    
    
