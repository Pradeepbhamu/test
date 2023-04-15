package com.sureshbhamu.test;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Second 
{
	@GetMapping("/model") 
			public String model()
			{
			return "This is my second project";
}
	
	
	@GetMapping("/api/demo")
	public String demo(String email ,String password) {
		System.out.println(" login api email"+email+"password" + password );
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root", "Pknice99@");
			Statement stmt= con.createStatement();
			String query ="insert into JavaUser values('"+email+"','"+password+"')";
			int i=stmt.executeUpdate(query);
			System.out.println("Number of rows inserted"+i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "this is login api"+email+password;
	}

	@PostMapping("/api/post")
	public String loginPost(String email ,String password) {
		System.out.println(" login api email"+email+"password" + password );
		return "this is login post api"+email+password;
	}
	
	
	
//	
//	LOGIN API
	
	
	
	
	@PostMapping("/api/login")
	public String login(String email,String password) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root", "Pknice99@");
		Statement stmt= con.createStatement();
		String query="select password from JavaUser where email='"+email+"'";
		ResultSet rs=stmt.executeQuery(query);
		if(rs.next()) {
			String pwd=rs.getString("password");
			if(pwd.equals(password)) {
				return"you are valid user";
			}else {
				return"password is wrong";
			}
				
		}
		else {
			return"you are not registered first";
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
		return "";
	}

	
	
	
	
	
	
	
//	SIGNUP API
	
	
	
	
	
	
	@GetMapping("/api/signup1")
	public Map signup(String email, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			Statement stmt=con.createStatement();
			String query="select * from JavaUser ";
			ResultSet rs= stmt.executeQuery(query);
			ArrayList list= new ArrayList();
			while(rs.next()) {
				Map map= new HashMap();
				map.put("email", rs.getString("email"));
				map.put("password", rs.getString("password"));
				list.add(map);
			}
			Map newmap =new HashMap();
			newmap.put("users", list);
			newmap.put("status", "ok");
			return newmap;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
//	COWIN STATES API
	
	
	

	@GetMapping("/api/v2/admin/location/states")
	public Map states() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			PreparedStatement stmt = con.prepareStatement("select * from states");
			ResultSet rs= stmt.executeQuery();
			ArrayList l= new ArrayList();
			while(rs.next()) {
				Map map=new HashMap();
				map.put("state_id" , rs.getString("state_id"));
				map.put("state_name" , rs.getString("state_name"));
				l.add(map);
			}
			Map data=new HashMap();
			data.put("states",l);
			data.put("ttl",24);
			return data;
			
			}catch (Exception e) {
	
	
			}
		return null;
			}
	
	
	
	
	
	
//	COWIN DISTRICT API
	
	
	
	
	
	@GetMapping("/api/v2/admin/location/district/{stateId}")
	public Map district(@PathVariable("stateId")String state_id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			PreparedStatement stmt = con.prepareStatement("select * from district where state_id=?");
			stmt.setInt(1, Integer.parseInt(state_id));
			ResultSet rs= stmt.executeQuery();
			ArrayList l= new ArrayList();
			while(rs.next()) {
				Map map=new HashMap();
				map.put("district_id" , rs.getString("district_id"));
				map.put("district_name" , rs.getString("district_name"));
				l.add(map);
			}
			Map data=new HashMap();
			data.put("district",l);
			data.put("ttl",24);
			return data;
			
			}catch (Exception e) {
	
	
			}
		return null;
			}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	findByDistrict API
	
	
	@GetMapping("/api/v2/appointment/sessions/public/findByDistrict")
	public Map findByDistrict(String district_id, String date) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			PreparedStatement stmt = con.prepareStatement
					("SELECT * FROM project.slots_available a join"
							+ " district b on a.district_id=b.district_id join states c"
							+ " on b.state_id=c.state_id where a.district_id=? AND a.vaccine_date=?;");
		
			stmt.setInt(1, Integer.parseInt(district_id));
			stmt.setString(2,date);
			ResultSet rs= stmt.executeQuery();
			ArrayList l= new ArrayList();
			while(rs.next()) {
				Map map=new HashMap();
				map.put("slot_id" , rs.getString("slot_id"));
				map.put("pin_code" , rs.getString("pin_code"));
				map.put("center_name" , rs.getString("center_name"));
				map.put("vaccine_date" , rs.getString("vaccine_date"));
				map.put("vaccine_dose" , rs.getString("vaccine_dose"));
				map.put("district_id" , rs.getString("district_id"));
				map.put("district_name" , rs.getString("district_name"));
				map.put("state_id" , rs.getString("state_id"));
				map.put("state_name" , rs.getString("state_name"));
				l.add(map);
			}
			Map data=new HashMap();
			data.put("session",l);
			data.put("ttl",24);
			return data;
			
			}catch (Exception e) {
	
	
			}
		return null;
			}
	
	
	
	
	













//	Find By pinCode
	
	
	
	
	@GetMapping("/api/v2/appointment/sessions/public/findByPin")
	public Map findByPin(String pincode,String date) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			PreparedStatement stmt = con.prepareStatement
					("SELECT * FROM slots_available a join district b on a.district_"
							+ "id=b.district_id join states c on"
							+ " b.state_id=c.state_id where a.pincode=?  AND a.vaccine_date=?;");
			stmt.setInt(1, Integer.parseInt(pincode));
			stmt.setString(2, date);
			ResultSet rs= stmt.executeQuery();
			ArrayList l= new ArrayList();
			while(rs.next()) {
				Map map=new HashMap();
				map.put("slot_id" , rs.getString("slot_id"));
				map.put("pincode" , rs.getString("pincode"));
				map.put("center_name" , rs.getString("center_name"));
				map.put("vaccine_date" , rs.getString("vaccine_date"));
				map.put("vaccine_dose" , rs.getString("vaccine_dose"));
				map.put("district_id" , rs.getString("district_id"));
				map.put("district_name" , rs.getString("district_name"));
				map.put("state_id" , rs.getString("state_id"));
				map.put("state_name" , rs.getString("state_name"));
				l.add(map);
			}
			Map data=new HashMap();
			data.put("session",l);
			data.put("ttl",24);
			return data;
		}catch (Exception e) {
			
			
		}
			
	
	
		
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	SHORT URL API
	
	
	
	
	
	
	@GetMapping("/shortUrl")
	public String shortUrl(String longUrl , String customUrl) {
		String newUrl="";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");
			if(customUrl==null ||(customUrl.isEmpty()&& customUrl.equals(""))) {
				while(true) {
				newUrl=createNewUrl(6);
				 PreparedStatement stmt = con.prepareStatement("select * from urls where shortUrl=?");
					stmt.setString(1,newUrl);
					ResultSet rs= stmt.executeQuery();
					if(rs.next()) {
						continue;
					}else {
						 PreparedStatement stmt1 = con.prepareStatement("insert into urls values(?,?)");
							stmt1.setString(1,longUrl);
							stmt1.setString(2,newUrl);
							int i=stmt1.executeUpdate();
							if(i==1) {
								return "your new short Url is tiny.cc/"+newUrl;
							}
					}
				}
					 
			}else {
				 PreparedStatement stmt = con.prepareStatement("select * from urls where shortUrl=?");
					stmt.setString(1,customUrl);
					ResultSet rs= stmt.executeQuery();
					if(rs.next()) {
						return"Already this custom url available";
					}else {
						 PreparedStatement stmt1 = con.prepareStatement("insert into urls values(?,?)");
							stmt1.setString(1,longUrl);
							stmt1.setString(2,customUrl);
							int i=stmt1.executeUpdate();
							if(i==1) {
								return "your new short Url is tiny.cc/"+customUrl;
							}
			}
			
			
			}
			}catch (Exception e) {
	
	
			}
		return null;
			}


	public String createNewUrl(int targetStringLength) {
		 int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		   
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		// TODO Auto-generated method stub
		return generatedString;
	}
	
	@GetMapping("/{url}")
	public ModelAndView goToMainWeb(@PathVariable("url")String url) {
		
		 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject", "root","Pknice99@");

		PreparedStatement stmt = con.prepareStatement("select * from urls where shortUrl=?");
		stmt.setString(1,url);
		ResultSet rs= stmt.executeQuery();
		String longUrl="";
		if(rs.next()) {
			longUrl=rs.getString("longUrl");
		}
		return  new ModelAndView ("redirect:"+longUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
}







