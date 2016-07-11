package ravens_roost_application;

public class Console {
	private String name;
	private int id;
	
	 public Console(String consoleNewName, int nid){
	    name = consoleNewName;
	    id = nid;
	 }
	 
	public String getConsoleName(){
		return name;
	}
	
	public int getConsoleID(){
		return id;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String toString(){
		return "" + name + "";
	}
}