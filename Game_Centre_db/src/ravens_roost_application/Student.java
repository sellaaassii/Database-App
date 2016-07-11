package ravens_roost_application;

public class Student {
	private int ID;
	private String firstName;
	private String lastName;
	
	public Student(int newID, String newFName, String newLName){
		ID = newID;
		firstName = newFName;
		lastName = newLName;
	}
	
	public String getStudentFirstName(){
		return firstName;
	}
	
	public String getStudentLastName(){
		return lastName;
	}
	
	public int getStudentId(){
		return ID;
	}
	
	public void setStudentID(int newID){
		ID = newID;
	}
	
	public void setStudentFirstName(String newFName){
		firstName = newFName;
	}
	
	public void setStudentLastName(String newLastName){
		lastName = newLastName;
	}
	
	public String toString(){
		return "" + firstName + " " + lastName + ",  " + ID; 
	}
}