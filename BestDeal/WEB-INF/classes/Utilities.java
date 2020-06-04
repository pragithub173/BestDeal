import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;//extra
import java.util.regex.Pattern;//extra

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				if(session.getAttribute("usertype").equals("manager"))
				{
					result = result + "<li><a href='ProductModify?button=Addproduct'><span class='glyphicon'>Addproduct</span></a></li>"
						+ "<li><a href='ProductModify?button=Updateproduct'><span class='glyphicon'>Updateproduct</span></a></li>"
						+"<li><a href='ProductModify?button=Deleteproduct'><span class='glyphicon'>Deleteproduct</span></a></li>"
						+"<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
						+"<li><a href='Inventory'><span class='glyphicon'>Inventory</span></a></li>"
						+"<li><a href='SalesReport'><span class='glyphicon'>Salesreport</span></a></li>"
						+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				
				else if(session.getAttribute("usertype").equals("retailer")){
					result = result + "<li><a href='Registration'><span class='glyphicon'>Create Customer</span></a></li>"
						+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				else
				{
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			    }
			}
			else
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
				result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}
	
	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				hm=MySqlDataStoreUtilities.selectUser();//correct
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				orderPayments =MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=size + 1;
					 
			}
			return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String,Console> allconsoles = new HashMap<String,Console> ();
			HashMap<String,Tablet> alltablets = new HashMap<String,Tablet> ();
			HashMap<String,Game> allgames = new HashMap<String,Game> ();
			HashMap<String,Laptop> alllaptops = new HashMap<String,Laptop> ();
			HashMap<String,Voice> allvoices = new HashMap<String,Voice> ();
			HashMap<String,FitnessWatch> allfitnesswatches = new HashMap<String,FitnessWatch> ();
			HashMap<String,SmartWatch> allsmartwatches = new HashMap<String,SmartWatch> ();
			HashMap<String,Headphone> allheadphones = new HashMap<String,Headphone> ();
			HashMap<String,WirelessPlan> allwirelessplans = new HashMap<String,WirelessPlan> ();
			HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
		if(type.equals("consoles")){
			Console console;
			try{
			allconsoles = MySqlDataStoreUtilities.getConsoles();//sql
			
			}
			catch(Exception e){
				
			}
			console = SaxParserDataStore.consoles.get(name);
			OrderItem orderitem = new OrderItem(console.getName(), console.getPrice(), console.getImage(), console.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("games")){
			Game game = null;
			try{
			allgames = MySqlDataStoreUtilities.getGames();
			}
			catch(Exception e){
				
			}
			game = SaxParserDataStore.games.get(name);
			OrderItem orderitem = new OrderItem(game.getName(), game.getPrice(), game.getImage(), game.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("tablets")){
			Tablet tablet = null;
			try{
			alltablets = MySqlDataStoreUtilities.getTablets();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			tablet = SaxParserDataStore.tablets.get(name);
			OrderItem orderitem = new OrderItem(tablet.getName(), tablet.getPrice(), tablet.getImage(), tablet.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("laptops")){
			Laptop laptop = null;
			try{
			alllaptops = MySqlDataStoreUtilities.getLaptops();
			}
			catch(Exception e){
				
			}
			laptop = SaxParserDataStore.laptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(), laptop.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("voices")){
			Voice voice = null;
			try{
			allvoices = MySqlDataStoreUtilities.getVoices();
			}
			catch(Exception e){
				
			}
			voice = SaxParserDataStore.voices.get(name);
			OrderItem orderitem = new OrderItem(voice.getName(), voice.getPrice(), voice.getImage(), voice.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("fitnesswatches")){
			FitnessWatch fitnesswatch = null;
			try{
			allfitnesswatches = MySqlDataStoreUtilities.getFitnessWatches();
			}
			catch(Exception e){
				
			}
			fitnesswatch = SaxParserDataStore.fitnesswatches.get(name);
			OrderItem orderitem = new OrderItem(fitnesswatch.getName(), fitnesswatch.getPrice(), fitnesswatch.getImage(), fitnesswatch.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("smartwatches")){
			SmartWatch smartwatch = null;
			try{
			allsmartwatches = MySqlDataStoreUtilities.getSmartWatches();
			}
			catch(Exception e){
				
			}
			smartwatch = SaxParserDataStore.smartwatches.get(name);
			OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage(), smartwatch.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("headphones")){
			Headphone headphone = null;
			try{
			allheadphones = MySqlDataStoreUtilities.getHeadphones();
			}
			catch(Exception e){
				
			}
			headphone = SaxParserDataStore.headphones.get(name);
			OrderItem orderitem = new OrderItem(headphone.getName(), headphone.getPrice(), headphone.getImage(), headphone.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("wirelessplans")){
			WirelessPlan wirelessplan = null;
			try{
			allwirelessplans = MySqlDataStoreUtilities.getWirelessPlans();
			}
			catch(Exception e){
				
			}
			wirelessplan = SaxParserDataStore.wirelessplans.get(name);
			OrderItem orderitem = new OrderItem(wirelessplan.getName(), wirelessplan.getPrice(), wirelessplan.getImage(), wirelessplan.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("accessories")){	
			try{
			allaccessories = MySqlDataStoreUtilities.getAccessories();
			}
			catch(Exception e){
				
			}
			Accessory accessory = SaxParserDataStore.accessories.get(name); 
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
			orderItems.add(orderitem);
		}
		
	}
	
		//1 store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String customer){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
			// get the payment details file 
		try
		{
			orderPayments=MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{
			
		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
		if(!orderPayments.containsKey(orderId)){	
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into database
		try
		{	if(session.getAttribute("usertype").equals("retailer"))
			{
				MySqlDataStoreUtilities.insertOrder(orderId,customer,orderName,orderPrice,userAddress,creditCardNo);
			}else
				
				{MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);}
		}
		catch(Exception e)
		{
			System.out.println("inside exception file not written properly");
		}	
	}
     public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city,String retailername,String retailerstate,String productsale ,String manufacturerebate,String userid,String userage,String usergender,String useroccupation){
		String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,retailername,retailerstate,productsale,manufacturerebate,userid,userage,usergender,useroccupation);
		//String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
		if(!message.equals("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
		try
		{
			reviews=MongoDBDataStoreUtilities.selectReview();
		}
		catch(Exception e)
		{
			
		}
		if(reviews==null)
		{
			reviews = new HashMap<String, ArrayList<Review>>();
		}
			// if there exist product review already add it into same list for productname or create a new record with product name
			
		if(!reviews.containsKey(productname)){	
			ArrayList<Review> arr = new ArrayList<Review>();
			reviews.put(productname, arr);
		}
		ArrayList<Review> listReview = reviews.get(productname);		
		Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,retailername,retailerstate,productsale,manufacturerebate,userid,userage,usergender,useroccupation);
		//Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
		listReview.add(review);	
			
			// add Reviews into database
		
		return "Successfull";	
		}
	}
	//delete product
/*public void removeItemFromCart(String itemName) {
			ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
			int index = 0;


			for (OrderItem oi : orderItems) {
					if (oi.getName().equals(itemName)) {
							break;
					} else index++;
			}
			orderItems.remove(index);
	}

			//OrdersHashMap.orders.values(name);


			public boolean removeProduct(String productId, String catalog) {//
			        switch (catalog) {
			            case "console":
			                SaxParserDataStore.consoles.remove(productId);
			                return true;


			            case "game":

			                SaxParserDataStore.games.remove(productId);
			                return true;

			            case "tablet":

			                SaxParserDataStore.tablets.remove(productId);
			                return true;

			            case "laptop":

			                SaxParserDataStore.laptops.remove(productId);
			                return true;

			            case "voice":

			                SaxParserDataStore.voices.remove(productId);
			                return true;

			            case "fitnesswatch":

			                SaxParserDataStore.fitnesswatches.remove(productId);
			                return true;

			            case "smartwatch":

			                SaxParserDataStore.smartwatches.remove(productId);
			                return true;
						
						case "headphone":

			                SaxParserDataStore.headphones.remove(productId);
			                return true;
							
						case "wirelessplan":

			                SaxParserDataStore.wirelessplans.remove(productId);
			                return true;
							
							case "accessory":

			                SaxParserDataStore.accessories.remove(productId);
			                return true;
			        }
			        return false;
			    }

*/	
	// store the payment details for orders
	/*public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String customer){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file 
			try
			{
				orderPayments=MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{	
				if(session.getAttribute("usertype").equals("retailer"))
			{
				MySqlDataStoreUtilities.insertOrder(orderId,customer,orderName,orderPrice,userAddress,creditCardNo);
			}else
				
				{MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);}
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}
*/
/*2	public void storeNewOrder(int orderId, String orderName, String customerName, double orderPrice, String userAddress, String creditCardNo) {
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        String TOMCAT_HOME = System.getProperty("catalina.home");
        // get the payment details file
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\WebStore\\PaymentDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            orderPayments = (HashMap) objectInputStream.readObject();
        } catch (Exception ignored) {

        }
        if (orderPayments == null) {
            orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        }
        // if there exist order id already add it into same list for order id or create a new record with order id

        if (!orderPayments.containsKey(orderId)) {
            ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
            orderPayments.put(orderId, arr);
        }
        ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);

        OrderPayment orderpayment = new OrderPayment(orderId, customerName, orderName, orderPrice, userAddress, creditCardNo);
        listOrderPayment.add(orderpayment);

        // add order details into file
        updateOrderFile(orderPayments);

    }

		//Create Products
		public boolean createProduct(String id, String name, String price, String condition, String discount, String image, String type) {
        switch (type) {
            case "console":
                Console console = new Console();
                console.setId(id);
                console.setName(name);
                console.setPrice(Double.parseDouble(price));
              //  console.setRetailer(manufacturer);
                console.setCondition(condition);
                console.setDiscount(Double.parseDouble(discount));
                console.setImage(image);
                SaxParserDataStore.consoles.remove(id);
                SaxParserDataStore.consoles.put(id, console);

                return true;
            case "game":

                Game game = new Game();
                game.setId(id);
                game.setName(name);
                game.setPrice(Double.parseDouble(price));
                //game.setRetailer(manufacturer);
                game.setCondition(condition);
                game.setDiscount(Double.parseDouble(discount));
                game.setImage(image);
                SaxParserDataStore.games.remove(id);
                SaxParserDataStore.games.put(id, game);
                return true;
            case "tablet":

                Tablet tablet = new Tablet();
                tablet.setId(id);
                tablet.setName(name);
                tablet.setPrice(Double.parseDouble(price));
              //  tablet.setRetailer(manufacturer);
                tablet.setCondition(condition);
                tablet.setDiscount(Double.parseDouble(discount));
                tablet.setImage(image);
                SaxParserDataStore.tablets.remove(id);
                SaxParserDataStore.tablets.put(id, tablet);
                return true;
            case "laptop":

                Laptop laptop = new Laptop();
                laptop.setId(id);
                laptop.setName(name);
                laptop.setPrice(Double.parseDouble(price));
              //  laptop.setRetailer(manufacturer);
                laptop.setCondition(condition);
                laptop.setDiscount(Double.parseDouble(discount));
                laptop.setImage(image);
                SaxParserDataStore.laptops.remove(id);
                SaxParserDataStore.laptops.put(id, laptop);
                return true;
            case "voice":

                Voice voice = new Voice();
                voice.setId(id);
                voice.setName(name);
                voice.setPrice(Double.parseDouble(price));
              //  voice.setRetailer(manufacturer);
                voice.setCondition(condition);
                voice.setDiscount(Double.parseDouble(discount));
                voice.setImage(image);
                SaxParserDataStore.voices.remove(id);
                SaxParserDataStore.voices.put(id, voice);
                return true;
            case "fitnesswatch":

                FitnessWatch fitnesswatch = new FitnessWatch();
                fitnesswatch.setId(id);
                fitnesswatch.setName(name);
                fitnesswatch.setPrice(Double.parseDouble(price));
              //  fitnesswatch.setRetailer(manufacturer);
                fitnesswatch.setCondition(condition);
                fitnesswatch.setDiscount(Double.parseDouble(discount));
                fitnesswatch.setImage(image);
                SaxParserDataStore.fitnesswatches.remove(id);
                SaxParserDataStore.fitnesswatches.put(id, fitnesswatch);
                return true;
            case "smartwatch":

                SmartWatch smartwatch = new SmartWatch();
                smartwatch.setId(id);
                smartwatch.setName(name);
                smartwatch.setPrice(Double.parseDouble(price));
              //  smartwatch.setRetailer(manufacturer);
                smartwatch.setCondition(condition);
                smartwatch.setDiscount(Double.parseDouble(discount));
                smartwatch.setImage(image);
                SaxParserDataStore.smartwatches.remove(id);
                SaxParserDataStore.smartwatches.put(id, smartwatch);
                return true;
            case "headphone":

                Headphone headphone = new Headphone();
                headphone.setId(id);
                headphone.setName(name);
                headphone.setPrice(Double.parseDouble(price));
                //headphone.setRetailer(manufacturer);
                headphone.setCondition(condition);
                headphone.setDiscount(Double.parseDouble(discount));
                headphone.setImage(image);
                SaxParserDataStore.headphones.remove(id);
                SaxParserDataStore.headphones.put(id, headphone);
                return true;
			case "wirelessplan":

                WirelessPlan wirelessplan = new WirelessPlan();
                wirelessplan.setId(id);
                wirelessplan.setName(name);
                wirelessplan.setPrice(Double.parseDouble(price));
                //wirelessplan.setRetailer(manufacturer);
                wirelessplan.setCondition(condition);
                wirelessplan.setDiscount(Double.parseDouble(discount));
                wirelessplan.setImage(image);
                SaxParserDataStore.wirelessplans.remove(id);
                SaxParserDataStore.wirelessplans.put(id, wirelessplan);
                return true;	

						case "accessory":

						    Accessory accessory = new Accessory();
						    accessory.setId(id);
						    accessory.setName(name);
						    accessory.setPrice(Double.parseDouble(price));
						    //accessory.setRetailer(manufacturer);
						    accessory.setCondition(condition);
						    accessory.setDiscount(Double.parseDouble(discount));
						    accessory.setImage(image);
						    SaxParserDataStore.accessories.remove(id);
						    SaxParserDataStore.accessories.put(id, accessory);
						    return true;

        }
        return false;
    }



		///////////////////////////////////////////////////////////////////////////////////////////////

		public boolean updateProduct(String id, String name, String price, String condition, String discount, String image, String type) {
        switch (type) {
            case "console":
                Console console = new Console();
                console.setId(id);
                console.setName(name);
                console.setPrice(Double.parseDouble(price));
              //  console.setRetailer(manufacturer);
                console.setCondition(condition);
                console.setDiscount(Double.parseDouble(discount));
                console.setImage(image);
                SaxParserDataStore.consoles.remove(id);
                SaxParserDataStore.consoles.put(id, console);

                return true;
            case "game":

                Game game = new Game();
                game.setId(id);
                game.setName(name);
                game.setPrice(Double.parseDouble(price));
                //game.setRetailer(manufacturer);
                game.setCondition(condition);
                game.setDiscount(Double.parseDouble(discount));
                game.setImage(image);
                SaxParserDataStore.games.remove(id);
                SaxParserDataStore.games.put(id, game);
                return true;
            case "tablet":

                Tablet tablet = new Tablet();
                tablet.setId(id);
                tablet.setName(name);
                tablet.setPrice(Double.parseDouble(price));
              //  tablet.setRetailer(manufacturer);
                tablet.setCondition(condition);
                tablet.setDiscount(Double.parseDouble(discount));
                tablet.setImage(image);
                SaxParserDataStore.tablets.remove(id);
                SaxParserDataStore.tablets.put(id, tablet);
                return true;
            case "laptop":

                Laptop laptop = new Laptop();
                laptop.setId(id);
                laptop.setName(name);
                laptop.setPrice(Double.parseDouble(price));
              //  laptop.setRetailer(manufacturer);
                laptop.setCondition(condition);
                laptop.setDiscount(Double.parseDouble(discount));
                laptop.setImage(image);
                SaxParserDataStore.laptops.remove(id);
                SaxParserDataStore.laptops.put(id, laptop);
                return true;
            case "voice":

                Voice voice = new Voice();
                voice.setId(id);
                voice.setName(name);
                voice.setPrice(Double.parseDouble(price));
              //  voice.setRetailer(manufacturer);
                voice.setCondition(condition);
                voice.setDiscount(Double.parseDouble(discount));
                voice.setImage(image);
                SaxParserDataStore.voices.remove(id);
                SaxParserDataStore.voices.put(id, voice);
                return true;
            case "fitnesswatch":

                FitnessWatch fitnesswatch = new FitnessWatch();
                fitnesswatch.setId(id);
                fitnesswatch.setName(name);
                fitnesswatch.setPrice(Double.parseDouble(price));
              //  fitnesswatch.setRetailer(manufacturer);
                fitnesswatch.setCondition(condition);
                fitnesswatch.setDiscount(Double.parseDouble(discount));
                fitnesswatch.setImage(image);
                SaxParserDataStore.fitnesswatches.remove(id);
                SaxParserDataStore.fitnesswatches.put(id, fitnesswatch);
                return true;
            case "smartwatch":

                SmartWatch smartwatch = new SmartWatch();
                smartwatch.setId(id);
                smartwatch.setName(name);
                smartwatch.setPrice(Double.parseDouble(price));
              //  smartwatch.setRetailer(manufacturer);
                smartwatch.setCondition(condition);
                smartwatch.setDiscount(Double.parseDouble(discount));
                smartwatch.setImage(image);
                SaxParserDataStore.smartwatches.remove(id);
                SaxParserDataStore.smartwatches.put(id, smartwatch);
                return true;
            case "headphone":

                Headphone headphone = new Headphone();
                headphone.setId(id);
                headphone.setName(name);
                headphone.setPrice(Double.parseDouble(price));
                //headphone.setRetailer(manufacturer);
                headphone.setCondition(condition);
                headphone.setDiscount(Double.parseDouble(discount));
                headphone.setImage(image);
                SaxParserDataStore.headphones.remove(id);
                SaxParserDataStore.headphones.put(id, headphone);
                return true;
			case "wirelessplan":

                WirelessPlan wirelessplan = new WirelessPlan();
                wirelessplan.setId(id);
                wirelessplan.setName(name);
                wirelessplan.setPrice(Double.parseDouble(price));
                //wirelessplan.setRetailer(manufacturer);
                wirelessplan.setCondition(condition);
                wirelessplan.setDiscount(Double.parseDouble(discount));
                wirelessplan.setImage(image);
                SaxParserDataStore.wirelessplans.remove(id);
                SaxParserDataStore.wirelessplans.put(id, wirelessplan);
                return true;	

						case "accessory":

						    Accessory accessory = new Accessory();
						    accessory.setId(id);
						    accessory.setName(name);
						    accessory.setPrice(Double.parseDouble(price));
						    //accessory.setRetailer(manufacturer);
						    accessory.setCondition(condition);
						    accessory.setDiscount(Double.parseDouble(discount));
						    accessory.setImage(image);
						    SaxParserDataStore.accessories.remove(id);
						    SaxParserDataStore.accessories.put(id, accessory);
						    return true;

        }
        return false;
    }

		public boolean isContainsStr(String string) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(string);
        return m.matches();
    }

		public boolean isItemExist(String itemCatalog, String itemName) {

        HashMap<String, Object> hm = new HashMap<String, Object>();

        switch (itemCatalog) {
            case "console":
                hm.putAll(SaxParserDataStore.consoles);
                break;
            case "game":
                hm.putAll(SaxParserDataStore.games);
                break;
            case "tablet":
                hm.putAll(SaxParserDataStore.tablets);
                break;
            case "laptop":
                hm.putAll(SaxParserDataStore.laptops);
                break;
            case "voice":
                hm.putAll(SaxParserDataStore.voices);
                break;
            case "fitnesswatch":
                hm.putAll(SaxParserDataStore.fitnesswatches);
                break;
            case "smartwatch":
                hm.putAll(SaxParserDataStore.smartwatches);
                break;
            case "headphone":
                hm.putAll(SaxParserDataStore.headphones);
                break;
			case "wirelessplan":
                hm.putAll(SaxParserDataStore.wirelessplans);
                break;	
          case "accessory":
              hm.putAll(SaxParserDataStore.accessories);
              break;
        }
        return true;
    }


		public void removeOldOrder(int orderId, String orderName, String customerName) {
        String TOMCAT_HOME = System.getProperty("catalina.home");
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        ArrayList<OrderPayment> ListOrderPayment = new ArrayList<OrderPayment>();
        //get the order from file
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\hw1\\PaymentDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            orderPayments = (HashMap) objectInputStream.readObject();
        } catch (Exception e) {

        }
        //get the exact order with same ordername and add it into cancel list to remove it later
        for (OrderPayment oi : orderPayments.get(orderId)) {
            if (oi.getOrderName().equals(orderName) && oi.getUserName().equals(customerName)) {
                ListOrderPayment.add(oi);
                //pw.print("<h4 style='color:red'>Your Order is Cancelled</h4>");
//                        response.sendRedirect("SalesmanHome");
//                        return;
            }
        }
        //remove all the orders from hashmap that exist in cancel list
        orderPayments.get(orderId).removeAll(ListOrderPayment);
        if (orderPayments.get(orderId).size() == 0) {
            orderPayments.remove(orderId);
        }

        //save the updated hashmap with removed order to the file
        updateOrderFile(orderPayments);
    }



		public boolean updateOrderFile(HashMap<Integer, ArrayList<OrderPayment>> orderPayments) {
        String TOMCAT_HOME = System.getProperty("catalina.home");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME + "\\webapps\\hw1\\PaymentDetails.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orderPayments);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }
        return true;
    }

		public void updateOrder(int orderId, String customerName,
                            String orderName, double orderPrice, String userAddress, String creditCardNo) {
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        String TOMCAT_HOME = System.getProperty("catalina.home");
        // get the payment details file
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME + "\\webapps\\hw1\\PaymentDetails.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            orderPayments = (HashMap) objectInputStream.readObject();
        } catch (Exception ignored) {

        }
        if (orderPayments == null) {
            orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
        }
        // if there exist order id already add it into same list for order id or create a new record with order id

        if (!orderPayments.containsKey(orderId)) {
            ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
            orderPayments.put(orderId, arr);
        }
        ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);

        OrderPayment orderpayment = new OrderPayment(orderId, customerName, orderName, orderPrice, userAddress, creditCardNo);
        listOrderPayment.add(orderpayment);

        // add order details into file
        updateOrderFile(orderPayments);
    }
//
	*/
	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/
//add s
	public HashMap<String, Console> getconsole(){
			HashMap<String, Console> hm = new HashMap<String, Console>();
			hm.putAll(SaxParserDataStore.consoles);
			return hm;
	}
	
	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Game> getgame(){
			HashMap<String, Game> hm = new HashMap<String, Game>();
			hm.putAll(SaxParserDataStore.games);
			return hm;
	}
	
	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Tablet> gettablet(){
			HashMap<String, Tablet> hm = new HashMap<String, Tablet>();
			hm.putAll(SaxParserDataStore.tablets);
			return hm;
	}
	
	public HashMap<String, Laptop> getlaptop(){
			HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
			hm.putAll(SaxParserDataStore.laptops);
			return hm;
	}
	
	public HashMap<String, Voice> getvoice(){
			HashMap<String, Voice> hm = new HashMap<String, Voice>();
			hm.putAll(SaxParserDataStore.voices);
			return hm;
	}
	
	public HashMap<String, FitnessWatch> getfitnesswatch(){
			HashMap<String, FitnessWatch> hm = new HashMap<String, FitnessWatch>();
			hm.putAll(SaxParserDataStore.fitnesswatches);
			return hm;
	}
	
	public HashMap<String, SmartWatch> getsmartwatch(){
			HashMap<String, SmartWatch> hm = new HashMap<String, SmartWatch>();
			hm.putAll(SaxParserDataStore.smartwatches);
			return hm;
	}
	
	public HashMap<String, Headphone> getheadphone(){
			HashMap<String, Headphone> hm = new HashMap<String, Headphone>();
			hm.putAll(SaxParserDataStore.headphones);
			return hm;
	}
	
	public HashMap<String, WirelessPlan> getwirelessplan(){
			HashMap<String, WirelessPlan> hm = new HashMap<String, WirelessPlan>();
			hm.putAll(SaxParserDataStore.wirelessplans);
			return hm;
			
	}
	
	public HashMap<String, Accessory> getaccessory(){
			HashMap<String, Accessory> hm = new HashMap<String, Accessory>();
			hm.putAll(SaxParserDataStore.accessories);
			return hm;		
	}//
	
	
	/* getProducts Functions returns the Arraylist of consoles in the store.*/
//
	public ArrayList<String> getProductsconsole(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Console> entry : getconsole().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	
	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsgame(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Game> entry : getgame().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductstablet(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Tablet> entry : gettablet().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductslaptop(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Laptop> entry : getlaptop().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsvoice(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Voice> entry : getvoice().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsfitnesswatch(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, FitnessWatch> entry : getfitnesswatch().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductssmartwatch(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartWatch> entry : getsmartwatch().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsheadphone(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Headphone> entry : getheadphone().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductswirelessplan(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, WirelessPlan> entry : getwirelessplan().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsaccessory(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Accessory> entry : getaccessory().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	//start for hw7
	/*public HashMap<String,String> readOutputFile(){
		String csvFile = "C:/apache-tomcat-7.0.34/webapps/Tutorial_7/output.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy,2);
				prodRecmMap.put(prod_recm[0],prod_recm[1]);
            }
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		
		return prodRecmMap;
	}*/
	
	//end for hw7
	public boolean isContainsStr(String string) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(string);
        return m.matches();
	}
	
	public boolean isItemExist(String itemCatalog, String itemName) {

        HashMap<String, Object> hm = new HashMap<String, Object>();

        switch (itemCatalog) {
            case "Console":
                hm.putAll(SaxParserDataStore.consoles);
                break;
            case "Game":
                hm.putAll(SaxParserDataStore.games);
                break;
            case "Tablet":
                hm.putAll(SaxParserDataStore.tablets);
                break;
            case "Laptop":
                hm.putAll(SaxParserDataStore.laptops);
                break;
            case "Voice":
                hm.putAll(SaxParserDataStore.voices);
                break;
            case "FitnessWatch":
                hm.putAll(SaxParserDataStore.fitnesswatches);
                break;
            case "SmartWatch":
                hm.putAll(SaxParserDataStore.smartwatches);
                break;
            case "Headphone":
                hm.putAll(SaxParserDataStore.headphones);
                break;
			case "WirelessPlan":
                hm.putAll(SaxParserDataStore.wirelessplans);
                break;	
            case "Accessory":
                hm.putAll(SaxParserDataStore.accessories);
                break;
        }
        return true;
    }

	

}
