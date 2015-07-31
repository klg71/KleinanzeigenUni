package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import backend.User;

public class FileConnector {
	private String filename;
	
	public FileConnector(String filenname){
		this.filename=filenname;
	}
	
	public void saveUsers(HashMap<String,User> users) throws IOException{
		FileWriter fileWriter=new FileWriter(new File(filename));
		for(Map.Entry<String, User> entry : users.entrySet()){
			fileWriter.write(entry.getValue().getUsername()+":"+entry.getValue().getPasswordHash()+System.getProperty("line.separator"));
		}
		fileWriter.close();
	}
	
	public HashMap<String,User> loadUsers() throws FileNotFoundException{
		HashMap<String,User> users=new HashMap<String,User>();
		Scanner scanner=new Scanner(new File(filename));
		while(scanner.hasNextLine()) {
			User newUser=new User(scanner.nextLine());
			users.put(newUser.getUsername(), newUser);
		}
		scanner.close();
		return users;
	}
	
}
