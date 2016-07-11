package ravens_roost_application;

public class Game {
	private String name;
	private int consoleID;
	
	
	public Game(String newGame, int rConsole){
	    name = newGame;
	    consoleID = rConsole;
	 }
	 
	public String getGameName(){
		return name;
	}
	
	public int getGameConsoleID(){
		return consoleID;
	}
	
	public void setGameName(String newName){
		name = newName;
	}
	
	public void setGameConsoleID(int newConsole){
		consoleID = newConsole;
	}
	
	public String toString(){
		return "" + name + " ";  
	}
	
}