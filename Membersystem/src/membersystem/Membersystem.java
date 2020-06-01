package membersystem;

import java.sql.*;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Membersystem {

   // Sökväg till SQLite-databas. OBS! Ändra sökväg så att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:D:/Documents/Databases/Fitness AB/Gym.db";  
   // Namnet på den driver som används av java för att prata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  
   public static int MemberID = -1;
   public static int StaffID = -1;
   static Scanner scan = new Scanner(System.in);
 public static void main(String[] args) {
     System.out.println("");
     
     Stafflogin();
     
    
 }
 public static void addMember(){
     Connection conn = null;
             boolean quit = false;
        while(!quit){
     if(MemberID != -1 || StaffID != -1){
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
         conn = DriverManager.getConnection(DB_URL);
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
     {System.out.println("Please log in to continue");
      login();
     }
}}
 
  public static void addCourseListing(){
           boolean quit = false;
        while(!quit){
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
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("INSERT INTO GymCourse(CourseID, GymID, StaffID, Date, Time) VALUES(?, ?, ?, ?, ?)");
         statement.setInt(1, parsedCourse);
         statement.setInt(2, parsedGym);
         statement.setInt(3, parsedStaff);
         statement.setString(4, Date);
         statement.setString(5, Time);
         statement.executeUpdate();
         quit = true;
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
         Stafflogin();
     
     }
  }}
   public static void addCourse(){
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
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("INSERT INTO Course(CourseID, CourseName) VALUES(?, ?)");
         statement.setInt(1, parsedCourseID);
         statement.setString(2, Name);
         statement.executeUpdate();
         quit = true;
     }catch(SQLException ex){System.out.println(ex.toString());}}else{System.out.println("You are not logged in, please log in and try again.");
            Stafflogin();
           
        
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
        int test = courseStaff(parsedGymCourseID);
        

     try{  
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("INSERT INTO CourseParticipants(GymCourseID, MemberID, GymID, StaffID) VALUES(?, ?, ?, ?)");
         statement.setInt(1, parsedGymCourseID);
         statement.setInt(2, MemberID);
         statement.setInt(3, parsedGymID);
         statement.setInt(4, test);
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
    
  public static void searchListings(int GymID){
        Connection conn = null;
        
        try{
         conn = DriverManager.getConnection(DB_URL);
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM GymCourse JOIN Course ON Course.CourseID=GymCourse.CourseID JOIN Gym ON Gym.GymID=GymCourse.GymID JOIN Staff on Staff.StaffID=GymCourse.StaffID WHERE GymCourse.GymID = ?");
         statement.setInt(1, GymID);
         
         ResultSet rs = statement.executeQuery();
         while (rs.next()){
             System.out.println("Gym: " + rs.getString("GymName"));
             System.out.println("Pass: " + rs.getString("CourseName"));
             System.out.println("Datum: " + rs.getString("Date"));
             System.out.println("Tid: " + rs.getString("Time"));
             System.out.println("Lärare: " + rs.getString("Name"));
             System.out.println("\n");
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
   
      public static int Stafflogin(){
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
                Stafflogin();
                break;
        }
        
      if (input.equals("1")){return MemberID;
      }
      else{
          return StaffID;
}

      }
   }
  
  
  
