package electricitybillingsys;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customers {
    Config conf = new Config();
    
    public void manageCustomers(Scanner scan){
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Customer Management ===\n");
                System.out.println("1. View All Customers\n"
                        + "2. Add a Customer\n"
                        + "3. Remove a Customer\n"
                        + "4. Edit a Customer\n"
                        + "5. Go back..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();

                switch (opt) {
                    case 1:
                        System.out.println("\n------------------------------------------------------------------");
                        viewCustomers("SELECT * FROM tbl_customers");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addCustomer(scan);
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteCustomer(scan);
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editCustomer(scan);
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
    
    public void viewCustomers(String query){ 
        System.out.println("\n\t\t\t\t\t\t\t   === CUSTOMERS LIST ===\n");
       
        String[] Headers = {"ID", "First Name", "Last Name", "Address", "Email"};
        String[] Columns = {"c_id", "c_fname", "c_lname", "c_address", "c_email"};
        
        conf.viewRecords(query, Headers, Columns);
        
    }

    public void addCustomer(Scanner scan){
        System.out.println("\n\t\t=== ADD A NEW CUSTOMER ===\n");
        
        System.out.println("Enter Customer's Details:");
        
        System.out.print("\nFirst Name: ");
        String fname = scan.nextLine();
        
        System.out.print("Last Name: ");
        String lname = scan.nextLine();
        
        System.out.print("Address: ");
        String address = scan.nextLine();
        
        System.out.print("Email: ");
        String email = scan.nextLine();
        
        String sql = "INSERT INTO tbl_customers (c_fname, c_lname, c_address, c_email) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, address, email);
    }

    public void deleteCustomer(Scanner scan){
        System.out.println("\n\t\t=== REMOVE A CUSTOMER ===\n");
        
        System.out.print("Enter Customer ID to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM tbl_customers WHERE c_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editCustomer(Scanner scan){
        System.out.println("\n\t\t=== EDIT A CUSTOMER ===\n");
        
        int id;
        do{
            System.out.print("\nEnter Customer ID: ");
            id = scan.nextInt();
            if(!conf.doesIDExist("tbl_customers", "c_id", id)){
                System.out.println("Customer ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("tbl_customers", "c_id", id));
        scan.nextLine();
        
        System.out.println("Selected Record:");
        viewCustomers("SELECT * FROM tbl_customers WHERE c_id = " + id);
        
        System.out.println("Enter New Customer Details:");
        
        System.out.print("\nNew First Name: ");
        String fname = scan.nextLine();
        
        System.out.print("New Last Name: ");
        String lname = scan.nextLine();
        
        System.out.print("New Address: ");
        String address = scan.nextLine();
        
        System.out.print("New Email: ");
        String email = scan.nextLine();
        
        String sql = "UPDATE tbl_customers SET c_fname = ?, c_lname = ?, c_address = ?, c_email = ? WHERE c_id = ?";
        conf.updateRecord(sql, fname, lname, address, email, id);
    }
}
