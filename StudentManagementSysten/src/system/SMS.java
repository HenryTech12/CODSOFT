package system;
import factory.*;
import java.util.*;
import student.*;
import service.*;
import file.*;

public class SMS
{

	StudentFactory sf = new StudentFactory();
	private boolean stop = false;
	public void buildConsole() {
		
		System.out.println("Welcome to our Student Management System");
		System.out.println("Our Functionalities includes :");
		
		System.out.println("1. Add Student \t 2. Search Student");
		System.out.println();
		System.out.println("3. Delete Student \t 4. Update Student Info");
	    System.out.println();
		System.out.println("5. Display All Student   \t  6. Exit");
		System.out.println();
		System.out.println("7. Delete All Student");
		
		System.out.println();
		
		while(!stop) {
		 try {
			performOperation();
		  }
		  catch(Exception ex) {
			  ex.printStackTrace();
		  }
		}
		
	}
	
	public void performOperation() {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Choose Your Option \nEnter between [ 1 - 7 ]: ");
		int choice = sc.nextInt();
		sc.nextLine(); //clears buffer
		switch(choice) {
			case 1:
				System.out.println("CURRENT FUNCTION - ADD STUDENT");
				System.out.println("Enter Student Name: ");
				String name = sc.next();
				name += sc.nextLine();
				System.out.println("Enter Student RollNo: ");
				int rollNo = sc.nextInt();
				System.out.println("Enter Student Grade: ");
				String grade = sc.next();
				grade += sc.nextLine();

				if(!name.isEmpty() && rollNo > 0) {
					Student student =
					    new StudentBuilder()
						.setName(name)
						.setRollNo(rollNo)
						.setGrade(grade)
						.build();
					sf.addStudent(student);
				}
			    else {
					System.out.println("student details is not valid");
				}
				break;
			case 2:
				System.out.println("CURRENT FUNCTION - Search Student");
				System.out.println("Enter Student RollNo: ");
				int studentrollNo = sc.nextInt();
				Student s = sf.searchStudent(studentrollNo);
				if(!Objects.isNull(s)) {
					System.out.println("Student details successfully fetched.");
					System.out.println(s);
				}
				else {
					System.out.println("Invalid Roll No");
				}
				break;
				
			case 3:
				System.out.println("CURRENT FUNCTION - REMOVE STUDENT");
				System.out.println("Enter Student RollNo: ");
				int srollNo = sc.nextInt();
				sf.removeStudent(srollNo);
				break;
			case 4:
				System.out.println("CURRENT FUNCTION - UPDATE STUDENT DETAILS");
				System.out.println("Enter New Student Name: ");
				String uName = sc.next();
				uName += sc.nextLine();
				System.out.println("Enter current Roll No:");
				int urollNo = sc.nextInt();
				System.out.println("Enter New Grade: ");
				String uGrade = sc.next();
				uGrade += sc.nextLine();
				
				Student newStudent = 
					new StudentBuilder()
					.setName(uName)
					.setRollNo(urollNo)
					.setGrade(uGrade)
					.build();
				sf.updateStudent(newStudent);
				break;
			case 5:
				System.out.println("CURRENT FUNCTION - DISPLAY STUDENTS");
				List<Student> students = sf.displayStudents();
				
			    for(Student getStudent: students) {
					sf.showStudent(getStudent);
				}
				break;
			case 6:
				stop = true;
				System.out.println("Thank you for using our student management system");
				//System.exit(0); //stops application
				sc.close();
				break;
			case 7:
				FileFactory ff = sf.getFileFactory();
				if(!Objects.isNull(ff)) {
					ff.deleteAll();
					System.out.println("all student details has been cleared.");
				}
				break;
			default:
				System.out.println("Invalid Function, Enter Btw [ 1 - 6 ]");
				break;
		}
	}
}
