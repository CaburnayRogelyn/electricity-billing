package electricitybillingsys;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BillingStatements {
    Config conf = new Config();
    
    public void manageBillingStatements(Scanner scan) {
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Billing Statements Management ===\n");
                System.out.println("1. View All Billing Statements\n"
                        + "2. Add a Billing Statement\n"
                        + "3. Remove a Billing Statement\n"
                        + "4. Edit a Billing Statement\n"
                        + "5. Go back..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 

                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        viewBillingStatements("SELECT * FROM tbl_billingstatements");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addBillingStatement(scan);
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteBillingStatement(scan);
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editBillingStatement(scan);
                        break;           
                        
                    case 5:
                        System.out.println("\nGoing back to Main Menu...");
                        System.out.println("------------------------------------------------------------------");
                        break;
                        
                    default:
                        System.out.println("Invalid Option.");
                }
                System.out.println("\n");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                opt = -1; 
            }
        } while (opt != 5);
    }
    
    public void viewBillingStatements(String query) { 
        System.out.println("\n\t\t\t\t\t\t\t   === BILLING STATEMENTS LIST ===\n");
       
        String[] Headers = {"Billing ID", "Customer ID", "Billing Period", "Amount Due", "Due Date", "Status"};
        String[] Columns = {"b_id", "c_id", "b_period", "b_due", "b_duedate", "b_status"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addBillingStatement(Scanner scan) {
        System.out.println("\n\t\t=== ADD A NEW BILLING STATEMENT ===\n");
        
        System.out.println("Enter Billing Statement Details:");
        
        int c_id;
        do {
            System.out.print("\nCustomer ID: ");
            c_id = scan.nextInt();
            if (!conf.doesIDExist("customers", "c_id", c_id)) {
                System.out.println("Customer ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("customers", "c_id", c_id));
        scan.nextLine();
        
        System.out.print("Billing Period: ");
        String b_period = scan.nextLine();
        
        System.out.print("Amount Due: ");
        double b_due = scan.nextDouble();
        scan.nextLine();
        
        System.out.print("Due Date: ");
        String b_duedate = scan.nextLine();
        
        System.out.print("Status (Paid/Unpaid): ");
        String b_status = scan.nextLine();
        
        System.out.println("");
        String sql = "INSERT INTO tbl_billingstatements (c_id, b_period, b_due, b_duedate, b_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, c_id, b_period, b_due, b_duedate, b_status);
    }

    public void deleteBillingStatement(Scanner scan) {
        System.out.println("\n\t\t=== REMOVE A BILLING STATEMENT ===\n");
        
        System.out.print("Enter Billing ID you want to delete: ");
        int b_id = scan.nextInt();
        
        String sql = "DELETE FROM tbl_billingstatements WHERE b_id = ?";
        conf.deleteRecord(sql, b_id);
    }

    public void editBillingStatement(Scanner scan) {
        System.out.println("\n\t\t=== EDIT A BILLING STATEMENT ===\n");
        
        int b_id;
        do {
            System.out.print("\nEnter Billing ID: ");
            b_id = scan.nextInt();
            if(!conf.doesIDExist("tbl_billingstatements", "b_id", b_id)){
                System.out.println("Billing ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("tbl_billingstatements", "b_id", b_id));
        scan.nextLine();
        
        System.out.println("Selected Record:");
        viewBillingStatements("SELECT * FROM tbl_billingstatements WHERE b_id = " + b_id);
        
        System.out.println("Enter Updated Billing Statement Details:");
        
        int c_id;
        do {
            System.out.print("\nCustomer ID: ");
            c_id = scan.nextInt();
            if (!conf.doesIDExist("customers", "c_id", c_id)) {
                System.out.println("Customer ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("customers", "c_id", c_id));
        scan.nextLine();
        
        System.out.print("New Billing Period: ");
        String b_period = scan.nextLine();
        
        System.out.print("New Amount Due: ");
        double b_due = scan.nextDouble();
        scan.nextLine();
        
        System.out.print("New Due Date: ");
        String b_duedate = scan.nextLine();
        
        System.out.print("New Status (Paid/Unpaid): ");
        String b_status = scan.nextLine();
        
        System.out.println("");
        String sql = "UPDATE tbl_billingstatements SET c_id = ?, b_period = ?, b_due = ?, b_duedate = ?, b_status = ? WHERE b_id = ?";
        conf.updateRecord(sql, c_id, b_period, b_due, b_duedate, b_status, b_id);
    }
}
