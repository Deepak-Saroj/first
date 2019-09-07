import java.util.*;
import java.io.*;
import java.sql.*;
class Techgig
{
	/* Table create --
	create table dictionary (data varchar2(25));
	*/
	private static Statement st;
	private static Scanner user = new Scanner( System.in );
	
	//connect to database Logic
	static
	{   
		try
		{
			//if DataBase is not SQL12C then change driverclass Name
			String className="oracle.jdbc.driver.OracleDriver";
			//also change url userName and Password
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String userName="system";
			String password="deepak";

			Class.forName(className);
			Connection con=DriverManager.getConnection(url,userName,password);
			st=con.createStatement();
		}
		catch(ClassNotFoundException ce)
		{
			System.out.print("db class not found");
		}
		catch(Exception ce)
		{
			System.out.print("db url not found");
		}
		
	}
	//tacking input file and put in linkedhashSet object and also insert into database
	static
	{
	    System.out.print("Input File Name: ");
	    try
	    {
		    String inputFileName = user.nextLine().trim();
		    File input = new File(inputFileName);      
		    Scanner scan= new Scanner(input);
		    LinkedHashSet<String> ls=new LinkedHashSet<String>();    
		    while( scan.hasNext() )    
		    {
		      ls.add(scan.next().replaceAll("[\\W]",""));
		    }
		    for(String s:ls)
		    {
		    	dbOperation(s);
		    }
		    ls=null;
		    scan.close();
		}
		catch(Exception ce)
		{
			System.out.print("input file not found");
		} 
	}
	

	public static void main(String[] args)throws Exception
	{   
	   	boolean flag=true;
	   	while(flag)
	   	{	
		   	System.out.println("Enter search value...");
		   	String sq=user.next();
		    	if(executeOperation(sq))
		    		System.out.print("Available");
		    	else
		    		System.out.print("Not Available");
		    System.out.println("\n*************\nfor continue ... type   Y\n************");
		    if(!(user.next()).equalsIgnoreCase("Y"))
		    	flag= false;
		}
		user.close();
	}
	//insertion process in database
	private static void dbOperation(String input)
	{
		String query="insert into Dictionary values('"+input+"')";
		try{st.executeUpdate(query);}catch(Exception e){}
	}
	//searching process
	private static boolean executeOperation(String input)
	{
		String query="select data from Dictionary where data='"+input+"'";
		try{

			ResultSet rs= st.executeQuery(query);
			if(rs.next())
				return true;
			else return false;

		}catch(Exception e){return false;}
		
	}
}