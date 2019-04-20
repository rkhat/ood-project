package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controllers.Pages;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import model.Manager;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import model.enums.STATUS;
import util.StringHelper;
import javafx.fxml.FXMLLoader;

/**
*
* @author Alec Agnese, Rami El Khatib
*/
public class TextTester {//extends Application {
 
 public static void printMainMenu() {
   System.out.println("Menu:\n");
   System.out.println("1) Log in");
   System.out.println("2) Sign up");
   System.out.println("3) Exit");
   System.out.println();
   System.out.print("Selection: ");
 }
 
 public static void printLoggedInMenu() {
   System.out.println("Menu:\n");
   System.out.println("1) Add Credits");
   System.out.println("2) Add Vehicle");
   System.out.println("3) Park Vehicle");
   System.out.println("4) Check out");
   System.out.println("5) Log out");
   System.out.println();
   System.out.println("Selection: ");
 }
 
 public static int getInt(Scanner scan) {
   int input;
   while(true) {
     try {
       input = scan.nextInt();
       scan.nextLine();
       return input;
     } catch(Exception e) {
       scan.nextLine();
       System.err.println(e.toString());
       System.out.println();
     }
   }
 }
 
 public static double getDouble(Scanner scan) {
   double input;
   while(true) {
     try {
       input = scan.nextDouble();
       scan.nextLine();
       return input;
     } catch(Exception e) {
       scan.nextLine();
       System.err.println(e.toString());
       System.out.println();
     }
   }
 }
 
 public static String getString(Scanner scan, boolean beforeWhiteSpace) {
   String input, str;
   while(true) {
     try {
       input = scan.nextLine();
       str = input.trim();
       int i = 0;
       for (char c : str.toCharArray()) {
         if (c == ' ') break;
         i++;
       }
       if (i < str.length()) str = str.substring(0, i);
       return str;
     } catch(Exception e) {
       scan.nextLine();
       System.err.println(e.toString());
       System.out.println();
     }
   }
 }
 
 public static String getUserName(Scanner scan) {
   String stringInput, un;
   // enter username
   while(true) {
     System.out.println("Enter Username: ");
     stringInput = scan.nextLine();
     System.out.println();
     if (StringHelper.checkAlphaNumeric(stringInput, 1)) {
       un = stringInput;
       return un;
     }
     System.out.println("Username must be alphanumeric with at least one character\n");
   }
 }
 
 public static String getPassword(Scanner scan, boolean twice) {
   String stringInput, psw;
   while(true) {
     System.out.println("Enter Password: ");
     stringInput = scan.nextLine();
     System.out.println();
     if (StringHelper.checkAlphaNumeric(stringInput,6)) {
       psw = stringInput;
       if (!twice) return psw;
       System.out.println("Re-enter Password: ");
       stringInput = scan.nextLine();
       System.out.println();
       if (!stringInput.contentEquals(psw)) {
         System.out.println("Passwords must match, try again");
         System.out.println();
         continue;
       }
       return psw;
     }
     System.out.println("Password must be alphanumeric with at least 6 characters\n");
   }
 }
 
 public static void printVehicles(Manager app) {
   List<Vehicle> vehicles = new ArrayList<Vehicle>(app.getMember().getVehicles().values());
   for (Vehicle v : vehicles) {
     System.out.println(v.toString());
   }
 }
 
 public static void printSpots(Manager app) {
   List<Spot> spots = app.getSpotsAsList();
   for (Spot s : spots) {
     if (!s.isReserved()) {
       System.out.println(s.toString());
     }
   }
 }
 
 public static void runApp(Manager app, Scanner scan) {
   int integerInput;
   double doubleInput;
   String stringInput;
   STATUS status;
   
   boolean loggedIn = true;
   while (loggedIn) {
     printLoggedInMenu();
     integerInput = getInt(scan);
     System.out.println();
     switch(integerInput) {
     // add credits
     case 1:
       System.out.println("Enter credits amount: ");
       doubleInput = getDouble(scan);
       System.out.println();
       status = app.doAddCredits(doubleInput);
       if (status == STATUS.SUCCESS) {
         System.out.println("Added " + doubleInput + " credits");
         System.out.println("Total credits: " + app.getMember().getCredits());
         System.out.println();
       }
       else {
         System.err.println("Error: Amount cannot be negative");
         System.err.println();
       }
       break;
       
     // add vehicle
     case 2:
       System.out.println("Enter plate number: ");
       stringInput = getString(scan,true);
       System.out.println();
       status = app.doAddVehicle(stringInput);
       if (status == STATUS.SUCCESS) {
         System.out.println("Vehicle added");
         System.out.println();
         printVehicles(app);
         System.out.println();
       }
       else if (status == STATUS.PLATE_INVALID) {
         System.err.println("Error: Invalid plate, must be alphanumeric with 6 characters");
         System.err.println();
       }
       else if (status == STATUS.PLATE_DUPLICATE) {
         System.err.println("Error: Vehicle with same plate is already in system");
         System.err.println();
       }
       else {
         System.err.println("Error: Failed to add vehicle, reason unknown");
         System.err.println();
       }
       break;
       
     // park vehicle
     case 3:
       // make sure there are spots available
       if (!app.spotsAvailable()) {
         System.err.println("No spots available");
         System.err.println();
         break;
       }
       // make sure member does not have a reservation
       if (app.getMember().getReservation() != null) {
         System.out.println("You must check out before parking another vehicle");
         System.out.println();
         break;
       }
       // make sure the member has vehicles on account
       if (app.getMember().getVehicles().size() == 0) {
         System.err.println("Error: No vehicles to park");
         System.err.println();
         break;
       }
       // select vehicle
       printVehicles(app);
       System.out.println();
       System.out.println("Enter ID of vehicle to park");
       integerInput = getInt(scan);
       System.out.println();
       status = app.doSelectVehicle(integerInput);
       if (status == STATUS.SUCCESS) {
         System.out.println("Created Reservation with following vehicle:");
         System.out.println(app.getVehicle().toString());
         System.out.println();
         // select spot
         printSpots(app);
         System.out.println();
         System.out.println("Enter ID of spot to reserve");
         integerInput = getInt(scan);
         System.out.println();
         status = app.doSelectSpot(integerInput);
         if (status == STATUS.SUCCESS) {
           System.out.println("Spot reserved!");
           System.out.println();
           System.out.println("Reservation details:");
           System.out.println("===========================");
           System.out.println(app.getReservation().toString());
           System.out.println("===========================");
           System.out.println();
         }
         else {
           app.resetReservation();
           if (status == STATUS.SPOT_NOT_FOUND) {
             System.err.println("Error: Spot not found");
           }
           else if (status == STATUS.SPOT_RESERVED) {
             System.err.println("Error: Spot is already reserved");
           }
           else {
             System.err.println("Error: Failed to reserve selected spot");
           }
           System.err.println();
         }
       }
       else {
         if (status == STATUS.NO_SPOTS_AVAILABLE) {
           System.err.println("Error: No spots available");
         } 
         else if (status == STATUS.INVALID_ID) {
           System.err.println("Error: Invalid ID");
         }
         else {
           System.out.println("Failed to create reservation with selected vehicle");
         }
         app.resetReservation();
         System.err.println();
         break;
       }
       break;
       
     // check out
     case 4:
       if (app.getReservation() == null) {
         // no reservation, error
         System.err.println("Error: No reservations to check out");
         System.err.println();
       }
       else {
         // print the total and ask for payment
         System.out.println("Total: " + app.getReservation().getTotal());
         System.out.println();
         System.out.println("1) Pay");
         System.out.println("2) Cancel");
         System.out.println();
         System.out.print("Selection: ");
         integerInput = getInt(scan);
         System.out.println();
         switch (integerInput) {
         case 1:
           if (app.doCheckout()) {
             System.out.println("Check out succeeded!");
             System.out.println("Remaining credits " + app.getMember().getCredits());
             System.out.println();
           }
           else {
             System.out.println("Insufficient credits!");
             System.out.println();
           }
           break;
           
         case 2:
           break;
         }
       }
       break;
     
     // log out
     case 5:
       app.doLogOut();
       loggedIn = false;
       break;
     }
   }
 }
 
 public static void main(String[] args) {
   // create list of spots
   List<Spot> spotList = new ArrayList<Spot>(1);
   spotList.add(new Spot(0,0));
//   for (int i = 1; i <= 5; i++) {
//     for (int j = 1; j <= 2; j++) {
//       spotList.add(new Spot(i,j));
//     }
//   }
  
   // create map
   ParkingMap map = new ParkingMap(spotList);
  
   // Set up parking system
   ParkingSystem ps = ParkingSystem.getInstance();
   ps.setMap(map);
  
   // Set up app manager
   Manager.createInstance();
   Manager app = Manager.getInstance();
  
   // Set up scanner and input buffers
   Scanner scan = new Scanner(System.in);
   int integerInput;
   STATUS status;
  
   String un, psw;
   boolean exit = false;
  
   while(true) {
   
     printMainMenu();
    
     integerInput = getInt(scan);
     System.out.println();
    
     switch(integerInput) {
     // log in
     case 1:
       // enter user name
       un = getUserName(scan);
      
       // enter password
       psw = getPassword(scan,false);
      
       // try to log in
       if (app.doLogIn(un, psw) == STATUS.SUCCESS) {
         // success, logged in
         System.out.println("Logged in!");
         System.out.println();
         runApp(app,scan);
       }
       else {
         System.err.println("Invalid credentials");
         System.err.println();
       }
       break;
      
     // sign up
     case 2:
      // enter user name
      un = getUserName(scan);
      
      // enter password
      psw = getPassword(scan,true);
      
      // try to create the account
      status = app.doCreateAccount(un, psw);
      if (status == STATUS.SUCCESS) {
        // success, logged in
        System.out.println("Account created!");
        System.out.println();
        runApp(app,scan);
      }
      else {
        // failed, print error and return to main menu
        if (status == STATUS.USERNAME_INVALID) {
          System.err.println("Error: Username must be alphanumeric");
        }
        else if (status == STATUS.USERNAME_IN_USE) {
          System.err.println("Error: Username is already in use");
        }
        else if (status == STATUS.PASSWORD_INVALID) {
          System.err.println("Error: Password must be alphanumeric with at least 6 characters");
        }
        else {
          System.err.println("Error: Account creation failed, back to Main Menu:");
        }
        System.err.println();
        break;
      }
      break;
        
    // exit
    case 3:
      exit = true;
      break;
      
    // invalid number
    default:
      break;
    }
    if (exit) break;
  }
 }
}