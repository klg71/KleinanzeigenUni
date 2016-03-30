package backend;

import java.util.ArrayList;

public class History {
	private static ArrayList<HistoryEntry> history=new ArrayList<HistoryEntry>();
	
	
	public static ArrayList<HistoryEntry> getHistory(){
		return history;
	}
	public static void printHistory(){
		for(HistoryEntry entry:history){
			System.out.print(entry.getTimestamp().toGMTString()+": ");
			if(entry.getUndone()){
				System.out.print("UNDONE ");
			}
			System.out.print(entry.getCommand().getKeywords().get(0)+" ");
			for(String parameter:entry.getParameters()){
				System.out.print(parameter+" ");
			}
			System.out.println();
		}
	}
	public static void addCommand(Command command,boolean undone){
		history.add(new HistoryEntry(command, undone));
	}
}
