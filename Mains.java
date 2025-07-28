package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;
public class Mains  {
	String url = "jdbc:mysql://localhost:3306/"; // Database details
    String username = "root"; // MySQL credentials
    String password = "manas3216";
    Connection con=null;
    Scanner s= new Scanner(System.in);
    public Mains() throws Exception{
    
		con = DriverManager.getConnection(url, username, password);
		System.out.println("---Welcome to Student Management System---");
		System.out.println("Enter Username: ");
		String user=s.next();
		System.out.println("Enter Password: ");
		String Password=s.next();
		System.out.println("Enter Role: ");
		String Role=s.next();
		login(user,Password,Role);
    }
    public void login(String user,String Password,String Role) throws Exception {
    	Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery("Select * from users");
    	while(rs.next()) {
    		if(user.equals(rs.getString("username")) ) {
    			if(Password.equals(rs.getString("password")) ) {
    				if(Role.equals(rs.getString("role")) ) {
    					int id=rs.getInt("id");
    					String role=rs.getString("role");
    					System.out.println("---Login Succesfull---");
    					Menu(id,role);
    					break;
    				}
    			}
    		}
    	}
   }
    public void Menu(int id,String Role) {
    	if(Role=="Admin") {
    	System.out.println("----Operations provided by this System----");
    	System.out.println("1.All course names:");
    	System.out.println("2.Add course:");
    	System.out.println("3.Delete course.");
    	System.out.println("4.Enroll Student");
    	System.out.println("5.Enrolled Student course");
    	System.out.println("6.Delete Student");
    	System.out.println("7.Update student course Marks.");
    	System.out.println("8.Total Score");
    	System.out.println("9.Mark Attendace");
    	System.out.println("10.View Attendace");
    	System.out.println("11.Attendace Summary");
    	System.out.println("12.Attendace Average");
    	System.out.println("13.Submit Feedback");
    	System.out.println("14.View Feedback");
    	System.out.println("15.Top Performers");
    	System.out.println("16.Log Out");
    	int item;
    	do {
    		System.out.println("Operations Chosen: ");
    		item=s.nextInt();
    		switch(item) {
    		case 1:
    			course_name();
    			break;
    		case 2:
    			 add_course();
    			break;
    		case 3:
    			 del_course();
    			break;
    		case 4:
    			en_student();
    			break;
    		case 5:
    			en_student_course();
    			break;
    		case 6:
    			 del_student();
    			break;
    		case 7:
    			 student_marks();
    			break;
    		case 8:
    			total_marks();
    			break;
    		case 9:
    			mark_attendance();
    			break;
    		case 10:
    			view_attendance();
    			break;
    		case 11:
    			attendance_summary() ;
    			break;
    		case 12:
    			attendance_average();
    			break;
    		case 13:
    			submit_feedback();
    		case 14:
    			view_feedback();
    		case 15:
    			top_performers();
    		case 16:
    			System.out.println("----Logged Off----");
    			break;
    		default:
    			 System.out.println("Error");
    		}
    	}while(item!=16);
    	}
    	else if(Role=="Teacher") {
    		System.out.println("----Operations provided by this System----");
        	
        	System.out.println("1.Update student course Marks.");
        	System.out.println("2.Total Score");
        	System.out.println("3.Mark Attendace");
        	System.out.println("4.View Attendace");
        	System.out.println("5.Attendace Summary");
        	System.out.println("6.Attendace Average");
        	
        	System.out.println("7.View Feedback");
        	System.out.println("8.Top Performers");
        	System.out.println("9.Log Out");
        	int item;
        	do {
        		System.out.println("Operations Chosen: ");
        		item=s.nextInt();
        		switch(item) {
        		
        		case 1 :
        			 student_marks();
        			break;
        		case 2:
        			total_marks();
        			break;
        		case 3:
        			mark_attendance();
        			break;
        		case 4:
        			view_attendance();
        			break;
        		case 5:
        			attendance_summary() ;
        			break;
        		case 6:
        			attendance_average();
        			break;
        		
        		case 7:
        			view_feedback();
        			break;
        		case 8:
        			top_performers();
        			break;
        		case 9:
        			System.out.println("----Logged Off----");
        			break;
        		default:
        			 System.out.println("Error");
        		}
        	}while(item!=9);
    	}
    }
    public void course_name() {
        try {
            String query = "SELECT * FROM courses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("ID\tName\tDuration\tFees");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String duration = rs.getString("duration");
                double fees = rs.getDouble("fees");
                System.out.println(id + "\t" + name + "\t" + duration + "\t" + fees);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void add_course() {
        try {
            System.out.print("Enter Course Name: ");
            String name = s.next();
            System.out.print("Enter Duration: ");
            String duration = s.next();
            System.out.print("Enter Fees: ");
            double fees = s.nextDouble();
            
            String query = "INSERT INTO courses (name, duration, fees) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, duration);
            pst.setDouble(3, fees);
            
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Course added successfully!");
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void del_course() {
        try {
            System.out.print("Enter Course ID to delete: ");
            int id = s.nextInt();
            
            String query = "DELETE FROM courses WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("No course found with that ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void en_student() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();
            System.out.print("Enter Course ID: ");
            int courseId = s.nextInt();

            String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student enrolled successfully.");
            } else {
                System.out.println("Enrollment failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void en_student_course() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();

            String sql = "SELECT c.id, c.name, c.duration, c.fees " +
                         "FROM enrollments e " +
                         "JOIN courses c ON e.course_id = c.id " +
                         "WHERE e.student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);

            ResultSet rs = pst.executeQuery();
            System.out.println("Courses enrolled:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Duration: " + rs.getString("duration") +
                                   ", Fees: " + rs.getDouble("fees"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void del_student() {
        try {
            System.out.print("Enter Student ID to delete: ");
            int studentId = s.nextInt();

            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with that ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void student_marks() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();
            System.out.print("Enter Course ID: ");
            int courseId = s.nextInt();
            System.out.print("Enter Marks: ");
            int marks = s.nextInt();

            // Check if entry exists
            String check = "SELECT * FROM results WHERE student_id = ? AND course_id = ?";
            PreparedStatement pstCheck = con.prepareStatement(check);
            pstCheck.setInt(1, studentId);
            pstCheck.setInt(2, courseId);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                // update
                String update = "UPDATE results SET marks = ? WHERE student_id = ? AND course_id = ?";
                PreparedStatement pst = con.prepareStatement(update);
                pst.setInt(1, marks);
                pst.setInt(2, studentId);
                pst.setInt(3, courseId);
                pst.executeUpdate();
                System.out.println("Marks updated.");
            } else {
                // insert
                String insert = "INSERT INTO results (student_id, course_id, marks) VALUES (?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(insert);
                pst.setInt(1, studentId);
                pst.setInt(2, courseId);
                pst.setInt(3, marks);
                pst.executeUpdate();
                System.out.println("Marks added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void total_marks() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();

            String sql = "SELECT SUM(marks) as total_marks FROM results WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Total Marks: " + rs.getInt("total_marks"));
            } else {
                System.out.println("No records found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mark_attendance() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();
            System.out.print("Enter Course ID: ");
            int courseId = s.nextInt();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = s.next();
            System.out.print("Enter Status (Present/Absent): ");
            String status = s.next();

            String sql = "INSERT INTO attendance (student_id, course_id, date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            pst.setDate(3, Date.valueOf(date));
            pst.setString(4, status);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Attendance marked.");
            } else {
                System.out.println("Failed to mark attendance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void view_attendance() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();

            String sql = "SELECT * FROM attendance WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Date: " + rs.getDate("date") +
                                   ", Course ID: " + rs.getInt("course_id") +
                                   ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void attendance_summary() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();

            String sql = "SELECT status, COUNT(*) as count " +
                         "FROM attendance " +
                         "WHERE student_id = ? " +
                         "GROUP BY status";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("status") + ": " + rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void attendance_average() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();
            System.out.print("Enter Date1: ");
            String dateString = s.next();
            LocalDate da = LocalDate.parse(dateString);
            Date date=Date.valueOf(da);
            System.out.print("Enter Date2: ");
            String dateString2 = s.next();
            LocalDate da2 = LocalDate.parse(dateString2);
            Date date2=Date.valueOf(da2);
            String sql = "SELECT status, COUNT(*) as count " +
                         "FROM attendance " +
                         "WHERE student_id = ? and DATE  BETWEEN ? AND ? " +
                         "GROUP BY status";
            String sql2="SELECT SUM(count) as total FROM ("+sql+")";
            PreparedStatement pst = con.prepareStatement(sql);
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst.setDate(1,date);
            pst.setDate(2,date2);
            pst2.setInt(1, studentId);
            pst2.setDate(2,date);
            pst2.setDate(3, date2);
            ResultSet rs = pst.executeQuery();
            ResultSet rs2 = pst2.executeQuery();
            int present=0;
            while (rs.next()) {
                if(rs.getString("status").equals("Present")) {
                	 present=rs.getInt("count");
                }
            }
            int avg=(present/rs2.getInt("total"))*100;
            System.out.println("Attendance Rate: "+avg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void submit_feedback() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = s.nextInt();
            System.out.print("Enter Course ID: ");
            int courseId = s.nextInt();
            s.nextLine(); // consume leftover newline
            System.out.print("Enter Feedback: ");
            String feedback = s.nextLine();

            String sql = "INSERT INTO feedback (student_id, course_id, feedback_text, submitted_on) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            pst.setString(3, feedback);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Feedback submitted!");
            } else {
                System.out.println("Failed to submit feedback.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void view_feedback() {
        try {
            System.out.print("Enter Course ID: ");
            int courseId = s.nextInt();

            String sql = "SELECT * FROM feedback WHERE course_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, courseId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id") +
                                   ", Date: " + rs.getDate("submitted_on") +
                                   ", Feedback: " + rs.getString("feedback_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void top_performers() {
        try {
            String sql = "SELECT student_id, SUM(marks) as total_marks " +
                         "FROM results " +
                         "GROUP BY student_id " +
                         "ORDER BY total_marks DESC " +
                         "LIMIT 5";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("Top performers:");
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id") +
                                   ", Total Marks: " + rs.getInt("total_marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
	
}
