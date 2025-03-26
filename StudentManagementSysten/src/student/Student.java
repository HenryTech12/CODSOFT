package student;
import java.io.*;

public class Student implements Serializable
{
	private String name;
	private int rollNo;
	private String grade;
	
	
	
 	public Student(StudentBuilder builder) {
		this.name = builder.getName();
		this.rollNo = builder.getRollNo();
		this.grade = builder.getGrade();
	}
	
	public Student() {
		
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setRollNo(int rollNo)
	{
		this.rollNo = rollNo;
	}

	public int getRollNo()
	{
		return rollNo;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}

	public String getGrade()
	{
		return grade;
	}

	@Override
	public String toString()
	{
		// TODO: Implement this method
		return "[ Name: "+name+", rollNo: "+rollNo+", Grade: "+grade+" ]";
	}
}
