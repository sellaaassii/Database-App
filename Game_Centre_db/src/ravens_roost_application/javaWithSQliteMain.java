package ravens_roost_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/*
This code has hard coded values for the database name, table names and names of the columns. The code is consistent with the
following sqlite3 database schema:

sqlite> .schema
create table if not exists students(
	student_number text primary key not null,
	number_of_appearances integer default 0 not null,
	fName text not null,
	lName text not null
);

create table if not exists consoles(
	consoleID integer primary key not null,
	console_name text not null
);

create table if not exists games(
	date_added text not null,
	consoleID integer not null,
	gameName text,
	primary key (consoleID,gameName),
	foreign key (consoleID) references consoles(consoleID)
);

create table if not exists outcomes(
	student_number integer not null,
	gameName text not null,
	consoleID integer not null,
	game_won text,
	updated text,
	primary key (consoleID, gameName, consoleID),
	foreign key (student_number) references student(student_number),
	foreign key (gameName) references game(gameName),
	foreign key (consoleID) references consoles(consoleID)
);

*/


public class javaWithSQliteMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Java With SQLite example");
		GUI frame = null;
		

		//Connect to database
        try {
        	
        	//direct java to the sqlite-jdbc driver jar code
        	// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
			
			//create connection to a database in the project home directory.
			//if the database does not exist one will be created in the home directory
		    
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:mytest.db");
			
			//HARD CODED DATABASE NAME:
			Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
		       //create a statement object which will be used to relay a
		       //sql query to the database
		     Statement stat = database.createStatement();

		    /*
		     * SQLite supports in-memory databases, which does not create any database files
		     * To use in memory database in your Java code, get the database connection as follows:
		     *
		     * Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
		     * 
		    */
			
		   
                //Query database for initial contents for GUI

	            String sqlQueryString = "select * from students where student_number in (select student_number from outcomes where updated is NULL);";
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Student> students = new ArrayList<Student>();

		        ResultSet rs = stat.executeQuery(sqlQueryString);
		        while (rs.next()) {
		            //System.out.print("code: " + rs.getString("code"));
		            //System.out.println(" title = " + rs.getString("title"));
		            Student newStudent = new Student(rs.getInt("student_number"), rs.getString("fName"), rs.getString("lName"));
		            System.out.println(newStudent.getStudentFirstName());
		            students.add(newStudent);
		        }
		        rs.close(); //close the query result table
		        
		        sqlQueryString = "select * from games;";
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Game> games = new ArrayList<Game>();

		        rs = stat.executeQuery(sqlQueryString);
		        while (rs.next()) {
		            //System.out.print("code: " + rs.getString("code"));
		            //System.out.println(" title = " + rs.getString("title"));
		            Game newGame = new Game(rs.getString("gameName"), rs.getInt("consoleID"));
		            games.add(newGame);
		        }
		        rs.close(); //close the query result table
		        
		        sqlQueryString = "select * from consoles;";
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Console> consoles = new ArrayList<Console>();

		        rs = stat.executeQuery(sqlQueryString);
		        while (rs.next()) {
		            //System.out.print("code: " + rs.getString("code"));
		            //System.out.println(" title = " + rs.getString("title"));
		            Console newConsole = new Console(rs.getString("console_name"), rs.getInt("consoleID"));
		            consoles.add(newConsole);
		        }
		        rs.close();
				
		        //Create GUI with knowledge of database and initial query content
		        frame =  new GUI("Ravens Roost Database", database, stat, students, games, consoles); //create GUI frame with knowledge of the database
		        
		        //Leave it to GUI to close database
		        //conn.close(); //close connection to database			
												
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //make GUI visible
		frame.setVisible(true);
	}
}

