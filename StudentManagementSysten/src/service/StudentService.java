package service;

import student.Student;
import java.util.List;

public interface StudentService
{
	
	void addStudent(Student student);
	void removeStudent(int rollNo);
	List<Student> displayStudents();
	Student searchStudent(int rollNo);
	Student updateStudent(Student newData);
}
