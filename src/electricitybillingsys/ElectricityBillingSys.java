package electricitybillingsys;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ElectricityBillingSys {
    static Scanner scan = new Scanner(System.in);    
    
    static Customers customerConf = new Customers();
    static BillingStatements billingConf = new BillingStatements();
        
    public static void main(String[] args) {
        
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Electricity Billing System ===\n");
                System.out.println("1. Customers\n"
                        + "2. Billing Statements\n"
                        + "3. Reports\n"
                        + "4. Exit");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();
                System.out.println("");
                
                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        customerConf.manageCustomers(scan);
                        break;
                        
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        billingConf.manageBillingStatements(scan);
                        break;

                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        generateReport();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                opt = -1; 
            }
        } while (opt != 4);        
    }
    
    private static void generateReport() {
        Config conf = new Config();
        
        customerConf.viewCustomers("SELECT * FROM tbl_customers"); 
        
        int id;
        do{
            System.out.print("\nEnter Customer ID: ");
            id = scan.nextInt();
            if(!conf.doesIDExist("tbl_customers", "c_id", id)){
                System.out.println("Customer ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("tbl_customers", "c_id", id));
        
        String fname = conf.getDataFromID("tbl_customers", id, "c_id", "c_fname");
        String lname = conf.getDataFromID("tbl_customers", id, "c_id", "c_lname");
        String address = conf.getDataFromID("tbl_customers", id, "c_id", "c_address");
        String email = conf.getDataFromID("tbl_customers", id, "c_id", "c_email");
        
        String idForm = String.format("Customer ID : %-35s Address : %-25s", id, address);
        String nameForm = String.format("Name        : %-35s Email   : %-25s",  (fname + " " + lname), email);
        
        System.out.printf("\n%72s\n\n", "=== INDIVIDUAL REPORT ===");
        System.out.printf("%108s\n", idForm);
        System.out.printf("%108s\n\n", nameForm);
        
        System.out.println("Billing Statements:");
        String sql = "SELECT bs.b_id, bs.b_period, bs.b_due, bs.b_duedate, bs.b_status "
                + "FROM tbl_billingstatements bs WHERE c_id = " + id;
        String[] columnHeaders = {"Statement ID", "Billing Period", "Amount Due", "Due Date", "Status"};
        String[] columnNames = {"b_id", "b_period", "b_due", "b_duedate", "b_status"};
        conf.viewRecords(sql, columnHeaders, columnNames);
    }
}
