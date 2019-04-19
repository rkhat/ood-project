package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import model.Manager;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import vc.Pages;
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
     if (ParkingSystem.checkAlphaNumeric(stringInput, 1)) {
       un = stringInput;
       return un;
     }
     System.out.println("Username must be alphanumeric with at least one character\n");
   }
 }
 
 public static String getPassword(Scanner scan) {
   String stringInput, psw;
   while(true) {
     System.out.println("Enter Password: ");
     stringInput = scan.nextLine();
     System.out.println();
     if (ParkingSystem.checkAlphaNumeric(stringInput,6)) {
       psw = stringInput;
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
   List<Spot> spots = app.getSpots();
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
       if (app.doAddCredits(doubleInput)) {
         System.out.println("Added " + doubleInput + " credits");
         System.out.println("Total credits: " + app.getMember().getCredits());
         System.out.println();
       }
       else {
         System.out.println("Failed to add credits");
         System.out.println();
       }
       break;
       
     // add vehicle
     case 2:
       System.out.println("Enter plate number: ");
       stringInput = getString(scan,true);
       System.out.println();
       if (app.doAddVehicle(stringInput)) {
         System.out.println("Vehicle added");
         System.out.println();
         printVehicles(app);
         System.out.println();
       }
       else {
         System.out.println("Failed to add vehicle");
         System.out.println();
       }
       break;
       
     // park vehicle
     case 3:
       if (app.getMember().getReservation() != null) {
         System.out.println("You must check out before parking another vehicle");
         System.out.println();
         break;
       }
       if (app.getMember().getVehicles().size() == 0) {
         System.out.println("No vehicles to park");
         System.out.println();
         break;
         
       }
       // select vehicle
       printVehicles(app);
       System.out.println();
       System.out.println("Enter ID of vehicle to park");
       integerInput = getInt(scan);
       System.out.println();
       if (app.doSelectVehicle(integerInput)) {
         System.out.println("Created Reservation with following vehicle:");
         System.out.println(app.getVehicle().toString());
         System.out.println();
         // select spot
         printSpots(app);
         System.out.println();
         System.out.println("Enter ID of spot to reserve");
         integerInput = getInt(scan);
         System.out.println();
         if (app.doSelectSpot(integerInput)) {
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
           System.out.println("Failed to reserve selected spot");
           System.out.println();
         }
       }
       else {
         app.resetReservation();
         System.out.println("Failed to create reservation with selected vehicle");
         System.out.println();
       }
       break;
       
     // check out
     case 4:
       if (app.getReservation() == null) {
         // no reservation, error
         System.out.println("No reservations to check out");
         System.out.println();
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
   List<Spot> spotList = new ArrayList<Spot>(10);
   for (int i = 1; i <= 5; i++) {
     for (int j = 1; j <= 2; j++) {
       spotList.add(new Spot(i,j));
     }
   }
  
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
   String stringInput;
   double doubleInput;
   char charInput;
  
   String un, psw;
   boolean exit = false, restart = false, loggedIn = false;
  
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
       psw = getPassword(scan);
      
       // try to log in
       if (app.doLogIn(un, psw)) {
         // success, logged in
         System.out.println("Logged in!");
         System.out.println();
         runApp(app,scan);
       }
       else {
         System.out.println("Invalid credentials");
         System.out.println();
       }
       break;
      
     // sign up
     case 2:
      // enter user name
      un = getUserName(scan);
      
      // enter password
      psw = getPassword(scan);
      
      // try to create the account
      if (app.doCreateAccount(un, psw)) {
        // success, logged in
        System.out.println("Account created!");
        System.out.println();
        runApp(app,scan);
      }
      else {
        // failed, back to main menu
        System.out.println("Account creation failed, back to Main Menu:");
        System.out.println();
        restart = true;
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