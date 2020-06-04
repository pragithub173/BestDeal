import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Phones")

public class GamesList extends HttpServlet {

	/* Games Page Displays all the Games and their Information in Game Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Games type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String,Game> allgames = new HashMap<String,Game> ();
        try{
		     allgames = MySqlDataStoreUtilities.getGames();
		}
		catch(Exception e)
		{
			
		}
		
		HashMap<String, Game> hm = new HashMap<String, Game>();
		
		if(CategoryName==null)
		{
			hm.putAll(allgames);
			name = "";
		}
		else
		{
		  if(CategoryName.equals("google"))
		  {
			for(Map.Entry<String,Game> entry : allgames.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Google"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Google";
		  }
		  else if(CategoryName.equals("samsung"))
		  {
			for(Map.Entry<String,Game> entry : allgames.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Samsung";
		  }
		  else if(CategoryName.equals("huawei"))
		  {
			for(Map.Entry<String,Game> entry : allgames.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Huawei"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Huawei";
		  }
		  else if(CategoryName.equals("nokia"))
		  {
			for(Map.Entry<String,Game> entry : allgames.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Nokia"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Nokia";
		  }
		  else if(CategoryName.equals("moto"))
		  {
			for(Map.Entry<String,Game> entry : allgames.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Moto"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Moto";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Games and Games information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Phones</a>"); //prints phones
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Game> entry : hm.entrySet()){
			Game game = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+game.getName()+"</h3>");
			pw.print("<strong>"+ "$" + game.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/games/"+game.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='games'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='games'>"+
					"<input type='hidden' name='price' value='"+game.getPrice()+"'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='games'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div><div class='clear'></div>");		
		utility.printHtml("Footer.html");
		
	}

}
