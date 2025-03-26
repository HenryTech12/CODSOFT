package factory;
import service.*;
import student.*;
import java.util.*;
import java.util.function.*;
import file.*;

public class StudentFactory implements StudentService
{

	private List<Student> students = new ArrayList<>();
	private FileFactory factory;
	
	public StudentFactory() {
		factory = new FileFactory();
		if(factory.extractData() != null) {
			students = factory.extractData();
		}
		System.out.println("yo");
	}
	@Override
	public void addStudent(final Student student)
	{
		// TODO: Implement this method
		Predicate<Student> p = new Predicate<Student>() {
			@Override
			public boolean test(Student s) {
				return (student.getRollNo() == s.getRollNo());
			}
		};
		Optional<Student> optionalS =  students.stream().filter(p).findFirst();
		if(!optionalS.isPresent()) {
			students.add(student);
			factory.save(students);
			System.out.println("student details has been successfully added to system");
		}
		else {
		 	System.out.println("roll no already exists, try using a different roll No.");
		}
	}

	@Override
	public Student searchStudent(final int rollNo)
	{
		// TODO: Implement this method
		Predicate<Student> p = new Predicate<Student>() {
			public boolean test(Student s) {
				return (s.getRollNo() == rollNo);
			}
		};
		Optional<Student> opStudent = students.stream().
					filter(p).findFirst();
		if(opStudent.isPresent()) 
			return opStudent.orElse(new Student());
		else
			return null;
	}

	@Override
	public List<Student> displayStudents()
	{
		// TODO: Implement this method
		return students;
	}

	@Override
	public void removeStudent(int rollNo)
	{
		// TODO: Implement this method
		boolean response = students.remove(searchStudent(rollNo));
	    if(response) {
			factory.save(students);
			System.out.println("student details has been removed from system.");
		} else {
			System.out.println("student with rollNo: "+rollNo+" not found");
		}
	}

	@Override
	public Student updateStudent(Student newData)
	{
		Student student = searchStudent(newData.getRollNo());
		if(!Objects.isNull(student)) {
			Student updated = 
			   new StudentBuilder().setName(newData.getName())
			   .setRollNo(newData.getRollNo())
			   .setGrade(newData.getGrade())
			   .build();
			System.out.println("Updated Student Details: ");
			System.out.println(updated);
			boolean removed = students.remove(student);
			if(removed) {
				students.add(updated);
				factory.save(students);
			}
			return updated;
		}
		// TODO: Implement this method
		System.out.println("Student details update failed, unmatched rollNo");
		return student;
	}

	public void showStudent(Student s) {
		System.out.println("-------------------------");
		System.out.println("|    Student Details   |");
		System.out.println("|  Name: "+s.getName()+"          |");
		System.out.println("|  RollNo: "+s.getRollNo()+"        |");
		System.out.println("|  Grade: "+s.getGrade()+"            |");
		System.out.println("-------------------------");
	}
	
	public FileFactory getFileFactory() {
		return factory;
	}
	

}
