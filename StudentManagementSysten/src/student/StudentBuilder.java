package student;

public class StudentBuilder
{
	
	private String name;
	private int rollNo;
	private String grade;

	public String getName()
	{
		return name;
	}

	public int getRollNo()
	{
		return rollNo;
	}

	public String getGrade()
	{
		return grade;
	}
	
	
	public StudentBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public StudentBuilder setRollNo(int rollNo) {
		this.rollNo = rollNo;
		return this;
	}
	
	public StudentBuilder setGrade(String grade) {
		this.grade = grade;
		return this;
	}
	
	public Student build() {
		return new Student(this);
	}
}
