package backend;

import java.util.ArrayList;
import java.util.Date;

public class HistoryEntry {
	private Command command;
	private Boolean undone;
	private Date timestamp;
	private ArrayList<String> parameters;
	
	public ArrayList<String> getParameters() {
		return parameters;
	}
	public HistoryEntry(Command command, Boolean undone) {
		super();
		this.command = command;
		this.undone = undone;
		this.timestamp=new Date();
		
		parameters=new ArrayList<String>();
		for(String parameter:command.getParameters()){
			parameters.add(parameter);
		}
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	public Boolean getUndone() {
		return undone;
	}
	public void setUndone(Boolean undone) {
		this.undone = undone;
	}
	
}
