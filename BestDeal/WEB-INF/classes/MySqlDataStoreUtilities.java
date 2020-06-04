import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;
static String message;
public static String getConnection()
{

	try
	{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pradeep","root","root");							
	message="Successfull";
	return message;
	}
	catch(SQLException e)
	{
		message="unsuccessful";
		     return message;
	}
	catch(Exception e)
	{
		message=e.getMessage();
		return message;
	}
}

public static void Insertproducts()
{
	try{
		
		
		getConnection();
		
		
		String truncatetableacc = "delete from Product_accessories;";
		PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
		pstt.executeUpdate();
		
		String truncatetableprod = "delete from  Productdetails;";
		PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
		psttprod.executeUpdate();
		
				
		
		String insertProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productInventory)" +
		"VALUES (?,?,?,?,?,?,?,?,?);";
		for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
		{   
			String name = "accessories";
	        Accessory acc = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,acc.getId());
			pst.setString(3,acc.getName());
			pst.setDouble(4,acc.getPrice());
			pst.setString(5,acc.getImage());
			pst.setString(6,acc.getRetailer());
			pst.setString(7,acc.getCondition());
			pst.setDouble(8,acc.getDiscount());
			pst.setInt(9,acc.getInventory());
			
			pst.executeUpdate();
			
			
		}
		
		for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet())
		{   
	        Console con = entry.getValue();
			String name = "consoles";
			
						
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,con.getId());
			pst.setString(3,con.getName());
			pst.setDouble(4,con.getPrice());
			pst.setString(5,con.getImage());
			pst.setString(6,con.getRetailer());
			pst.setString(7,con.getCondition());
			pst.setDouble(8,con.getDiscount());
			pst.setInt(9,con.getInventory());
			
			pst.executeUpdate();
			try{
			HashMap<String,String> acc = con.getAccessories();
			String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
			"VALUES (?,?);";
			for(Map.Entry<String,String> accentry : acc.entrySet())
			{
				PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
				pstacc.setString(1,con.getId());
				pstacc.setString(2,accentry.getValue());
				pstacc.executeUpdate();
			}
			}catch(Exception et){
				et.printStackTrace();
			}
		}
		for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet())
		{   
			String name = "games";
	        Game game = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,game.getId());
			pst.setString(3,game.getName());
			pst.setDouble(4,game.getPrice());
			pst.setString(5,game.getImage());
			pst.setString(6,game.getRetailer());
			pst.setString(7,game.getCondition());
			pst.setDouble(8,game.getDiscount());
			pst.setInt(9,game.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,Tablet> entry : SaxParserDataStore.tablets.entrySet())
		{   
			String name = "tablets";
	        Tablet tablet = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,tablet.getId());
			pst.setString(3,tablet.getName());
			pst.setDouble(4,tablet.getPrice());
			pst.setString(5,tablet.getImage());
			pst.setString(6,tablet.getRetailer());
			pst.setString(7,tablet.getCondition());
			pst.setDouble(8,tablet.getDiscount());
			pst.setInt(9,tablet.getInventory());
			
			pst.executeUpdate();
			
			
		}
		/*for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet())
		{   
			String name = "games";
	        Game game = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,game.getId());
			pst.setString(3,game.getName());
			pst.setDouble(4,game.getPrice());
			pst.setString(5,game.getImage());
			pst.setString(6,game.getRetailer());
			pst.setString(7,game.getCondition());
			pst.setDouble(8,game.getDiscount());
			
			pst.executeUpdate();
			
			
		}*/
		for(Map.Entry<String,Laptop> entry : SaxParserDataStore.laptops.entrySet())
		{   
			String name = "laptops";
	        Laptop laptop = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,laptop.getId());
			pst.setString(3,laptop.getName());
			pst.setDouble(4,laptop.getPrice());
			pst.setString(5,laptop.getImage());
			pst.setString(6,laptop.getRetailer());
			pst.setString(7,laptop.getCondition());
			pst.setDouble(8,laptop.getDiscount());
			pst.setInt(9,laptop.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,Voice> entry : SaxParserDataStore.voices.entrySet())
		{   
			String name = "voices";
	        Voice voice = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,voice.getId());
			pst.setString(3,voice.getName());
			pst.setDouble(4,voice.getPrice());
			pst.setString(5,voice.getImage());
			pst.setString(6,voice.getRetailer());
			pst.setString(7,voice.getCondition());
			pst.setDouble(8,voice.getDiscount());
			pst.setInt(9,voice.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
		{   
			String name = "fitnesswatches";
	        FitnessWatch fitnesswatch = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,fitnesswatch.getId());
			pst.setString(3,fitnesswatch.getName());
			pst.setDouble(4,fitnesswatch.getPrice());
			pst.setString(5,fitnesswatch.getImage());
			pst.setString(6,fitnesswatch.getRetailer());
			pst.setString(7,fitnesswatch.getCondition());
			pst.setDouble(8,fitnesswatch.getDiscount());
			pst.setInt(9,fitnesswatch.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,SmartWatch> entry : SaxParserDataStore.smartwatches.entrySet())
		{   
			String name = "smartwatches";
	        SmartWatch smartwatch = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartwatch.getId());
			pst.setString(3,smartwatch.getName());
			pst.setDouble(4,smartwatch.getPrice());
			pst.setString(5,smartwatch.getImage());
			pst.setString(6,smartwatch.getRetailer());
			pst.setString(7,smartwatch.getCondition());
			pst.setDouble(8,smartwatch.getDiscount());
			pst.setInt(9,smartwatch.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
		{   
			String name = "headphones";
	        Headphone headphone = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,headphone.getId());
			pst.setString(3,headphone.getName());
			pst.setDouble(4,headphone.getPrice());
			pst.setString(5,headphone.getImage());
			pst.setString(6,headphone.getRetailer());
			pst.setString(7,headphone.getCondition());
			pst.setDouble(8,headphone.getDiscount());
			pst.setInt(9,headphone.getInventory());
			
			pst.executeUpdate();
			
			
		}
		for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
		{   
			String name = "wirelessplans";
	        WirelessPlan wirelessplan = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,wirelessplan.getId());
			pst.setString(3,wirelessplan.getName());
			pst.setDouble(4,wirelessplan.getPrice());
			pst.setString(5,wirelessplan.getImage());
			pst.setString(6,wirelessplan.getRetailer());
			pst.setString(7,wirelessplan.getCondition());
			pst.setDouble(8,wirelessplan.getDiscount());
			pst.setInt(9,wirelessplan.getInventory());
			
			pst.executeUpdate();
			
			
		}
		
	}catch(Exception e)
	{
  		e.printStackTrace();
	}
} 

public static HashMap<String,Console> getConsoles()
{	
	HashMap<String,Console> hm=new HashMap<String,Console>();
	try 
	{
		getConnection();
		
		String selectConsole="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectConsole);
		pst.setString(1,"consoles");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Console con = new Console(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), con);
				con.setId(rs.getString("Id"));
				
				try
				{
				String selectaccessory = "Select * from Product_accessories where productName=?";
				PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
				pstacc.setString(1,rs.getString("Id"));
				ResultSet rsacc = pstacc.executeQuery();
				
				HashMap<String,String> acchashmap = new HashMap<String,String>();
				while(rsacc.next())
				{    
					if (rsacc.getString("accessoriesName") != null){
					
					 acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
					 con.setAccessories(acchashmap);
					}
					 
				}					
				}catch(Exception e)
				{
						e.printStackTrace();
				}
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Tablet> getTablets()
{	
	HashMap<String,Tablet> hm=new HashMap<String,Tablet>();
	try 
	{
		getConnection();
		
		String selectTablet="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTablet);
		pst.setString(1,"tablets");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Tablet tab = new Tablet(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), tab);
				tab.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Game> getGames()
{	
	HashMap<String,Game> hm=new HashMap<String,Game>();
	try 
	{
		getConnection();
		
		String selectGame="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectGame);
		pst.setString(1,"games");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Game game = new Game(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), game);
				game.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
//laptops
public static HashMap<String,Laptop> getLaptops()
{	
	HashMap<String,Laptop> hm=new HashMap<String,Laptop>();
	try 
	{
		getConnection();
		
		String selectLaptop="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectLaptop);
		pst.setString(1,"laptops");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Laptop laptop = new Laptop(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), laptop);
				laptop.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,Voice> getVoices()
{	
	HashMap<String,Voice> hm=new HashMap<String,Voice>();
	try 
	{
		getConnection();
		
		String selectVoice="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectVoice);
		pst.setString(1,"voices");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Voice voice = new Voice(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), voice);
				voice.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,FitnessWatch> getFitnessWatches()
{	
	HashMap<String,FitnessWatch> hm=new HashMap<String,FitnessWatch>();
	try 
	{
		getConnection();
		
		String selectFitnessWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectFitnessWatch);
		pst.setString(1,"fitnesswatches");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	FitnessWatch fitnesswatch = new FitnessWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), fitnesswatch);
				fitnesswatch.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,SmartWatch> getSmartWatches()
{	
	HashMap<String,SmartWatch> hm=new HashMap<String,SmartWatch>();
	try 
	{
		getConnection();
		
		String selectSmartWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartWatch);
		pst.setString(1,"smartwatches");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartWatch smartwatch = new SmartWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), smartwatch);
				smartwatch.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,Headphone> getHeadphones()
{	
	HashMap<String,Headphone> hm=new HashMap<String,Headphone>();
	try 
	{
		getConnection();
		
		String selectHeadphone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectHeadphone);
		pst.setString(1,"headphones");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Headphone headphone = new Headphone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), headphone);
				headphone.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}
public static HashMap<String,WirelessPlan> getWirelessPlans()
{	
	HashMap<String,WirelessPlan> hm=new HashMap<String,WirelessPlan>();
	try 
	{
		getConnection();
		
		String selectWirelessPlan="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectWirelessPlan);
		pst.setString(1,"wirelessplans");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	WirelessPlan wirelessplan = new WirelessPlan(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), wirelessplan);
				wirelessplan.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Accessory> getAccessories()
{	
	HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
	try 
	{
		getConnection();
		
		String selectAcc="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		pst.setString(1,"accessories");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Accessory acc = new Accessory(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), acc);
				acc.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		   
			String name = producttype;
	        			
			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,name);
			pst.setString(2,productId);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
			pst.setString(6,productManufacturer);
			pst.setString(7,productCondition);
			pst.setDouble(8,productDiscount);
			
			pst.executeUpdate();
			try{
				if (!prod.isEmpty())
				{
					String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
					"VALUES (?,?);";
					PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
					pst1.setString(1,prod);
					pst1.setString(2,productId);
					pst1.executeUpdate();
					
				}
			}catch(Exception e)
			{
				msg = "Erro while adding the product";
				e.printStackTrace();
		
			}
			
			
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount)
{ 
    String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=? where Id =?;" ;
		
		   
				        			
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);
			
			pst.setString(1,productName);
			pst.setDouble(2,productPrice);
			pst.setString(3,productImage);
			pst.setString(4,productManufacturer);
			pst.setString(5,productCondition);
			pst.setDouble(6,productDiscount);
			pst.setString(7,productId);
			pst.executeUpdate();
			
			
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
 return msg;	
}
public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
	try
	{
		
		getConnection();
		String deleteproductsQuery ="Delete from Productdetails where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,productId);
		
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}

public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			
	}
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo)
{
	try
	{
	Date current_date = new Date();
	SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		getConnection();
		
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo,orderTime)"
		+ "VALUES (?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.setString(7,SimpleDateFormat.format(current_date.getTime()));
		pst.execute();
	}
	catch(Exception e)
	{
	
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"));
			listOrderPayment.add(order);
					
		}
				
					
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


//add
public static HashMap<String, Product> selectOnSale() {
	HashMap<String, Product> hm = new HashMap<String, Product>();
	try {
		getConnection();

		String selectAcc = "select * from productdetails where productCondition = 'New'";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
	  //  pst.setString(1, "1");
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"),rs.getInt("productInventory"));
			hm.put(rs.getString("Id"), product);
			product.setId(rs.getString("Id"));
		}
	} catch (Exception e) {
	}
	return hm;
}

public static HashMap<String, Product> selectRebate() {
	HashMap<String, Product> hm = new HashMap<String, Product>();
	try {
		getConnection();

		String selectAcc = "select * from Productdetails where productDiscount > 0";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"), Double.parseDouble(rs.getString("productDiscount")));
			hm.put(rs.getString("Id"), product);
			product.setId(rs.getString("Id"));
		}
	} catch (Exception e) {
	}
	return hm;
}

public static HashMap<String, OrderPayment> selectSaleAmount() {
	HashMap<String, OrderPayment> hm = new HashMap<String, OrderPayment>();
	try {
		getConnection();

		String selectAcc = "select DISTINCT(temp.orderName),temp.saleAmount,customerorders.orderPrice from customerorders, (select orderName, count(orderName) as saleAmount from customerorders group by orderName) as temp where customerorders.orderName = temp.orderName";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		ResultSet rs = pst.executeQuery();

		int i = 0;
		while (rs.next()) {
			OrderPayment orderPayment = new OrderPayment(rs.getString("orderName"), rs.getDouble("orderPrice"), rs.getInt("saleAmount"));
			i++;
			hm.put(String.valueOf(i), orderPayment);
			//orderPayment.setOrderId(Integer.parseInt(rs.getString("Id")));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return hm;
}

public static HashMap<String, Product> selectInventory() {
  HashMap<String, Product> hm = new HashMap<String, Product>();
  try {
	  getConnection();

	  String selectAcc = "select * from productdetails";
	  PreparedStatement pst = conn.prepareStatement(selectAcc);
	  ResultSet rs = pst.executeQuery();

	  while (rs.next()) {
		  Product product = new Product(rs.getString("productName"), rs.getDouble("productPrice"),rs.getInt("productInventory"));
		  hm.put(rs.getString("Id"), product);
		  product.setId(rs.getString("Id"));
	  }
  } catch (Exception e) {
	  System.out.println("Exception from the database"+e);

  }
  System.out.println("Getting the data for the inventory ="+hm);
  return hm;
}

public static HashMap<String, OrderPayment> selectDailyTransaction() {
	HashMap<String, OrderPayment> hm = new HashMap<String, OrderPayment>();
	try {
		getConnection();

		String selectAcc = "SELECT count(orderTime) as soldAmount, orderTime from customerorders group by orderTime";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		ResultSet rs = pst.executeQuery();

		int i = 0;
		while (rs.next()) {
			OrderPayment orderPayment = new OrderPayment(rs.getInt("soldAmount"), rs.getDate("orderTime"));
			i++;
			hm.put(String.valueOf(i), orderPayment);
			//orderPayment.setId(rs.getString("Id"));
		}
	} catch (Exception e) {
	}
	return hm;
}
public static ArrayList<OrderPayment> selectDailyTransactionForChart() {
	ArrayList<OrderPayment> orderPaymentArrayList = new ArrayList<OrderPayment>();
	try {
		getConnection();

		String selectAcc = "SELECT count(orderTime) as soldAmount, orderTime from orders group by orderTime";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			OrderPayment orderPayment = new OrderPayment(rs.getInt("soldAmount"), rs.getDate("orderTime"));
			orderPaymentArrayList.add(orderPayment);
		}
	} catch (Exception e) {
	}
	return orderPaymentArrayList;
}

public static HashMap<String,Product> getData()
	{
		HashMap<String,Product> hm=new HashMap<String,Product>();
		try
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  productdetails";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	Product p = new Product(rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getString("ProductType"),rs.getDouble("productDiscount"),rs.getInt("productInventory"));
				hm.put(rs.getString("Id"), p);
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		return hm;			
	}


}