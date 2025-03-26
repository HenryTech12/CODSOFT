package service;
import student.*;
import java.util.*;

public interface FileService
{

	void save(List<Student> student);
	List<Student> extractData();
	void deleteAll();
}
