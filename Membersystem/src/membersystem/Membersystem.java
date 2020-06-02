package membersystem;

import java.sql.*;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Membersystem {

   // Sökväg till SQLite-databas. OBS! Ändra sökväg så att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/Users/David/Documents/Fitness AB Projekt/Gym.db";  
   // Namnet på den driver som används av java för att prata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  
   public static int MemberID = -1;
   public static int StaffID = -1;
   static Scanner scan = new Scanner(System.in);
 public static void main(String[] args) {
     System.out.println("");
     
    addMembership();
    
     
    
 }
 public static void addMember(){ //Function for adding members by using a scanner to take input values and put them in the database with prepared statements
     Connection conn = null;
             boolean quit = false;
        while(!quit){
     if(MemberID != -1 || StaffID != -1){ // <--- Checks if the user is logged in
        System.out.print("Full name");
        String Name = scan.nextLine();
        System.out.print("Personal number (YYYYMMDDXXXX): ");
        String PNr = scan.nextLine();
        System.out.print("Phone number");
        String PhoneNr = scan.nextLine();
        System.out.println("Email");
        String Email = scan.nextLine();
        System.out.println("Username");
        String Usr = scan.nextLine();
        System.out.println("Password");
        String Pass = scan.nextLine();
        
        long parsedPNr = Long.parseLong(PNr);
     try{
         conn = DriverManager.getConnection(DB_URL); //connects to database, takes the inputs from user and assigns them to the correct columns in the table.
         PreparedStatement statement = conn.prepareStatement("INSERT INTO Member(Name, PersonalNr, PhoneNr, Email, Username, Password) VALUES(?, ?, ?, ?, ?, ?)");
         statement.setString(1, Name);
         statement.setLong(2, parsedPNr);
         statement.setString(3, PhoneNr);
         statement.setString(4, Email);
         statement.setString(5, Usr);
         statement.setString(6, Pass);
         statement.executeUpdate();
     }catch(SQLException ex){System.out.println(ex.toString());}}
     else
     {System.out.println("Please log in to continue"); // If user is not logged in, prompts them to log in with the login method
      login();
     }
}}
 
  public static void addCourseListing(){ //Adds a course to the listings, these are the actual bookable courses
           boolean quit = false;
        while(!quit){ //While loop to return to this part of the function after logging in if the user isn't logged in
      Connection conn = null;
     if(StaffID != -1){
        System.out.print("CourseID");
        String CourseID = scan.nextLine();
        System.out.print("Gym");
        String GymID = scan.nextLine();
        System.out.print("Staff");
        String staff = scan.nextLine();
        System.out.println("Date YYMMDD");
        String Date = scan.nextLine();
        System.out.println("Time");
        String Time = scan.nextLine();
        
        int parsedCourse = Integer.parseInt(CourseID);
        int parsedGym = Integer.parseInt(GymID);
        int parsedStaff = Integer.parseInt(staff);

     try{
         conn = DriverManager.getConnection(DB_URL);//connects to database, takes the inputs from user and assigns them to the correct columns in the table.
         PreparedStatement statement = conn.prepareStatement("INSERT INTO GymCourse(CourseID, GymID, StaffID, Date, Time) VALUES(?, ?, ?, ?, ?)");
         statement.setInt(1, parsedCourse);
         statement.setInt(2, parsedGym);
         statement.setInt(3, parsedStaff);
         statement.setString(4, Date);
         statement.setString(5, Time);
         statement.executeUpdate();
         quit = true; //the loop ends here but is only reachable while logged in.
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
         staffLogin();
     
     }
  }}
  
  public static void addStaff(){ //Adds Staff
           boolean quit = false;
        while(!quit){ //While loop to return to this part of the function after logging in if the user isn't logged in
      Connection conn = null;
     if(StaffID != -1){
        System.out.print("Name");
        String StaffName = scan.nextLine();
        System.out.print("PhoneNumber");
        String PhoneNr = scan.nextLine();
        System.out.print("Email");
        String Email = scan.nextLine();
        System.out.println("Password for login");
        String Pass = scan.nextLine();


     try{
         conn = DriverManager.getConnection(DB_URL);//connects to database, takes the inputs from user and assigns them to the correct columns in the table.
         PreparedStatement statement = conn.prepareStatement("INSERT INTO Staff(Name, PhoneNr, Email, Password) VALUES(?, ?, ?, ?)");

         statement.setString(1, StaffName);
         statement.setString(2, PhoneNr);
         statement.setString(3, Email);
         statement.setString(4, Pass);
         statement.executeUpdate();
         quit = true; //the loop ends here but is only reachable while logged in.
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
         staffLogin();
     
     }
  }}
  
   public static void addCourse(){ //Adds courses to the Course table for use with addCourseListing();
             boolean quit = false;
        while(!quit){ 
     Connection conn = null;
     if(StaffID != -1){
        System.out.print("CourseID");
        String CourseID = scan.nextLine();
        System.out.print("Course Name");
        String Name = scan.nextLine();
        
        int parsedCourseID = Integer.parseInt(CourseID);
     try{  
         conn = DriverManager.getConnection(DB_URL);//connects to database, takes the inputs from user and assigns them to the correct columns in the table.
         PreparedStatement statement = conn.prepareStatement("INSERT INTO Course(CourseID, CourseName) VALUES(?, ?)");
         statement.setInt(1, parsedCourseID);
         statement.setString(2, Name);
         statement.executeUpdate();
         quit = true;
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
            staffLogin();
           
        
     } 
  
}}
    public static void addBooking(){
        boolean quit = false;
        while(!quit){
     Connection conn = null;
     if(MemberID != -1 || StaffID != -1){
        System.out.print("CourseID");
        String GymCourseID = scan.nextLine();
        System.out.println("Gym");
        String GymID = scan.nextLine();

        
        int parsedGymCourseID = Integer.parseInt(GymCourseID);
        int parsedGymID = Integer.parseInt(GymID);
        int Trainer = courseStaff(parsedGymCourseID);
        

     try{  
         conn = DriverManager.getConnection(DB_URL);//connects to database, takes the inputs from user and assigns them to the correct columns in the table.
         PreparedStatement statement = conn.prepareStatement("INSERT INTO CourseParticipants(GymCourseID, MemberID, GymID, StaffID) VALUES(?, ?, ?, ?)");
         statement.setInt(1, parsedGymCourseID);
         statement.setInt(2, MemberID);
         statement.setInt(3, parsedGymID);
         statement.setInt(4, Trainer);
         statement.executeUpdate();
         quit = true;
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
       login();
        
     }}}
    
    public static int courseStaff(int parsedGymCourseID){
        int Staff = 0;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement getstaff;
            getstaff = conn.prepareStatement("SELECT StaffID FROM GymCourse WHERE GymCourseID =" + parsedGymCourseID);
            ResultSet rs = getstaff.executeQuery();
         
         while (rs.next()){
            
                Staff = rs.getInt("StaffID");
         }
        }catch(SQLException ex){
            System.out.println(ex.toString());}
        return Staff;
    }
    
    public static void addMembership(){
        boolean quit = false;
        int inputID = 0;
        while(!quit){
        Connection conn = null;
        if(MemberID != -1 || StaffID != -1){
            if(MemberID != -1){
                inputID = MemberID;
            }else{
            System.out.println("Choose Member to add membership to:");
            inputID = scan.nextInt();
            
            }
            System.out.println("Choose Membership Tier:");
            String tierLV = scan.nextLine();
            System.out.println("Start Date DDMMYY:");
            String Start = scan.nextLine();
            System.out.println("End Date DDMMYY:");
            String End = scan.nextLine();
            System.out.println("Name:");
            String payName = scan.nextLine();
            System.out.println("AccountNumber:");
            String Account = scan.nextLine();
            System.out.println("Credit Card Number:");
            String CreditCard = scan.nextLine();
            
            int parsedAccount = Integer.parseInt(Account);
            int parsedCreditCard = Integer.parseInt(CreditCard);
            int tierNr = Integer.parseInt(tierLV);
            String tier = tierName(tierNr);
            try{
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO MembershipRegistration(MemberID, StartDate, EndDate, PaymentName, AccountNumber, CreditCardDetails, TierName) VALUES(?, ?, ?, ?, ?, ?, " + tier);
                statement.setInt(1, inputID);
                statement.setString(2, Start);
                statement.setString(3, End);
                statement.setString(4, payName);
                statement.setInt(5, parsedAccount);
                statement.setInt(6, parsedCreditCard);
                statement.setString(7, tier);
                statement.executeUpdate();
            }catch(SQLException ex){System.out.println(ex.toString());}
        }else{System.out.println("Please Log in to continue");
        login();
            
        }
        }
    }
    public static String tierName(int tierLevel){
        String tierName ="";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement getTier;
            getTier = conn.prepareStatement("SELECT TierName FROM MembershipTier WHERE tierLevelValue =" + tierLevel);
            ResultSet rs = getTier.executeQuery();
         
         while (rs.next()){
            
                tierName = rs.getString("TierName");
         }
        }catch(SQLException ex){
            System.out.println(ex.toString());}
        return tierName;
    }
    
    /*public static String memberName(int ID){
                String MemberName ="";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement getName;
            getName = conn.prepareStatement("SELECT Name FROM Member WHERE MemberID =" + ID);
            ResultSet rs = getName.executeQuery();
         
         while (rs.next()){
            
                MemberName = rs.getString("Name");
         }
        }catch(SQLException ex){
            System.out.println(ex.toString());}
        return MemberName;
    } */
    
      public static void updateMember(){
          
        Connection conn = null;
        boolean quit = false;
        String column = "";
        String value = "";
        
        System.out.println("Choose member to update: \nMemberID:");
        String test = scan.nextLine();
        int inputID = Integer.parseInt(test);
        
        while(quit != true){


        System.out.println("What do you want to update \n1 - Name \n2 - Phone number \n3 - Email \n4 - Username \n5 - Password \n 6 - Personal Number \n7 - Quit");
        String input = scan.nextLine();
        switch(input){ 
            case "1":
                column = "Name";
                System.out.println("Enter new value for selected field:\n");
                value = scan.nextLine();
                break;
            
            case "2":
                column = "PhoneNr";
                System.out.println("Enter new value for selected field:\n");
                value = scan.nextLine();
                break;
                
            case "3":
                column = "Email";
                System.out.println("Enter new value for selected field:\n");
                value = scan.nextLine();
                break;
            
            case "4":
                column = "Username";
                System.out.println("Enter new value for selected field:\n");
                value = scan.nextLine();
                break;
                
            case "5":
                column = "Password";
                System.out.println("Enter new value for selected field:\n");
                value = scan.nextLine();
                break;
                
            case "6":
                staffLogin();
                if(StaffID == -1){
                    System.out.println("Only Staff are allowed to change this value, please contact your local Gym");
                    break;
                }
                else{
                    column = "PersonalNr";
                    System.out.println("Enter new value for selected field:\n");
                    value = scan.nextLine();
                    break;
                     }   
            
            case "7":
                 quit = true;
                break;
        }

        
       if(MemberID == inputID || StaffID != -1){
           
          
        try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("UPDATE Member set " + column + " = ? WHERE MemberID = " + inputID);
         
            statement.setString(1, value);
        
            statement.executeUpdate();
         
        }catch(SQLException ex){System.out.println(ex.toString());}
       }else{if(MemberID != -1){
           System.out.println("You are not authorized to edit this member.");
       }else{
           System.out.println("Please log in to continue");
           login();
       }
       }

      }}
    
  public static void searchListings(){//Searchfunction for bookable courses
        Connection conn = null;
        String filterType = "";
        String value = "";
       
               System.out.println("What do you want to filter by? \n1 - Course \n2 - Date \n3 - Gym \n4 - Trainer");
        String input = scan.nextLine();
        switch(input){ //Switch fucntion to determine what value and filtertype is going to be, which is used in the SQL query to show courses
            case "1":
                filterType = "CourseID"; // int
                System.out.println("Input CourseID");
                value = scan.nextLine();
                break;
            
            case "2":
                filterType = "date"; // String
                System.out.println("Input Date MMDDYY");
                value = scan.nextLine();
                break;
                
            case "3":
                filterType = "GymID"; //int
                System.out.println("Input GymID");
                value = scan.nextLine();
                break;
            
            case "4":
                filterType = "StaffID"; //int
                System.out.println("Input StaffID");
                value = scan.nextLine();
                break;
        }
        
       
        try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM GymCourse JOIN Course ON Course.CourseID=GymCourse.CourseID JOIN Gym ON Gym.GymID=GymCourse.GymID JOIN Staff on Staff.StaffID=GymCourse.StaffID WHERE GymCourse." + filterType + "= ?");
         if(filterType == "date"){ // Since date is the only value that is not an integer we first check to see if the filterType was date
             statement.setString(1, value);
         }
         else{ //if the filtertype is anything other than date it is an int and has to be parsed, we also need to change the statement.setString to statement.setInt
             int parsedValue = Integer.parseInt(value);
             statement.setInt(1, parsedValue);
         }
         
         ResultSet rs = statement.executeQuery();
         if (rs.next() == false){ // This is used to print the courses found or to show an error message if there were no courses that matched the criteria of the search
             System.out.println("No results found. try changing your query or try again");
         }else{ do{
             System.out.println("Gym: " + rs.getString("GymName"));
             System.out.println("Pass: " + rs.getString("CourseName"));
             System.out.println("Datum: " + rs.getString("Date"));
             System.out.println("Tid: " + rs.getString("Time"));
             System.out.println("Lärare: " + rs.getString("Name"));
             System.out.println("\n");
         }while (rs.next());

         }
        }catch(SQLException ex){System.out.println(ex.toString());}}
  
   public static void searchMemberBooking(){
        Connection conn = null;
        System.out.println("MemberID");
        String Member = scan.nextLine();
        
        int parsedMemberID = Integer.parseInt(Member);
        try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM GymCourse JOIN Course ON Course.CourseID=GymCourse.CourseID JOIN Gym ON Gym.GymID=GymCourse.GymID JOIN Staff on Staff.StaffID=GymCourse.StaffID JOIN CourseParticipants on GymCourse.GymCourseID = CourseParticipants.GymCourseID WHERE CourseParticipants.MemberID = ?");
         statement.setInt(1, parsedMemberID);
         
         ResultSet rs = statement.executeQuery();
                  if (rs.next() == false){
             System.out.println("No results found. try changing your query or try again");
         }else{ do{
             System.out.println("Gym: " + rs.getString("GymName"));
             System.out.println("Pass: " + rs.getString("CourseName"));
             System.out.println("Datum: " + rs.getString("Date"));
             System.out.println("Tid: " + rs.getString("Time"));
             System.out.println("Lärare: " + rs.getString("Name"));
             System.out.println("\n");
         }while (rs.next());

         }
        }catch(SQLException ex){System.out.println(ex.toString());}}
      
   public static int Memberlogin(){
      
       Connection conn = null;
       MemberID = -1;
        System.out.print("Username");
        String Username = scan.nextLine();
        System.out.print("Password");
        String Password = scan.nextLine();
              try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("SELECT MemberID from Member WHERE Username=? and Password=?");
         statement.setString(1, Username);
         statement.setString(2, Password);
         
         ResultSet rs = statement.executeQuery();
         
         while (rs.next()){
            
                MemberID = rs.getInt("MemberID");
         }
        }catch(SQLException ex){System.out.println(ex.toString());}
            return MemberID;
            
            
            
   } 
   
      public static int staffLogin(){
          //boolean quit = false;
         // while(!quit){
       Connection conn = null;
       StaffID = -1;
        System.out.print("Staff ID");
        String Username = scan.nextLine();
        System.out.print("Password");
        String Password = scan.nextLine();
        
        int staffID = Integer.parseInt(Username);
              try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("SELECT StaffID from Staff WHERE StaffID=? and Password=?");
         statement.setInt(1, staffID);
         statement.setString(2, Password);
         
         ResultSet rs = statement.executeQuery();
                  
         while (rs.next()){
            
                StaffID = rs.getInt("StaffID");
            //    quit = true;
         }
        }catch(SQLException ex){System.out.println(ex.toString());}           
  // }
          return StaffID;
}
      public static int login(){
       System.out.println("1 - member login \n2 - Staff login");
        String input = scan.nextLine();
        switch(input){
            case "1":
                Memberlogin();
                break;
            
            case "2":
                staffLogin();
                break;
        }
        
      if (input.equals("1")){return MemberID;
      }
      else{
          return StaffID;
}

      }
   }
  
  
  
