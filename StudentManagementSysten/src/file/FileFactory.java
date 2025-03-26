package file;
import service.*;
import student.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class FileFactory implements FileService
{

	String path = System.getProperty("user.dir");
	
	@Override
	public void save(List<Student> students)
	{
		// TODO: Implement this method
	 try {
		File file = new File(path+"/data.txt");
		file.setExecutable(true);
		file.setWritable(true);
		file.setReadable(true);
		
		file.createNewFile();
		OutputStream is = new FileOutputStream(file);
	    ObjectOutputStream writer = new ObjectOutputStream(is);
		writer.writeObject(students);
		
	  }
	  catch(IOException ie) {
		  System.out.println("error: "+ie.getMessage());
	  }
	}

	@Override
	public List<Student> extractData()
	{
		// TODO: Implement this method
		try {
			File file = new File(path+"/data.txt");
			file.setExecutable(true);
			file.setWritable(true);
			file.setReadable(true);
			
			
			if(file.exists()) {
			  InputStream is = new FileInputStream(file);
			  ObjectInputStream read = new ObjectInputStream(is);
			  List<Student> students = (List<Student>) read.readObject();
			  return students;
			}
		}
		catch(IOException | ClassNotFoundException ie) {
			System.out.println("error: "+ie.getMessage());
		}
		return null;
	}

	@Override
	public void deleteAll()
	{
		// TODO: Implement this method
			File file = new File(path+"/data.txt");
			file.setExecutable(true);
			file.setWritable(true);
			file.setReadable(true);
		if(file.exists()) {
			boolean deleted = file.delete();
		}
	}

	
	
}
