import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WirelessPlan")

public class WirelessPlanList extends HttpServlet {

	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, WirelessPlan> hm = new HashMap<String, WirelessPlan>();

		if (CategoryName == null)	
		{
			hm.putAll(SaxParserDataStore.wirelessplans);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("net10")) 
			{	
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Net10"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Net10";
			} 
			else if (CategoryName.equals("mint"))
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Mint"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Mint";
			} 
			else if (CategoryName.equals("total")) 
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Total"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Total";
			}
			else if (CategoryName.equals("tmobile"))
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Tmobile"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Tmobile";
			} 
			else if (CategoryName.equals("ultra"))
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessplans.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Ultra"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Ultra";
			} 
	    }

		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name +" Wireless Plan's</a>"); 
		pw.print("</h2><div class='entry'><table id='bestseller'>"); 
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, WirelessPlan> entry : hm.entrySet()) {
			WirelessPlan WirelessPlan = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + WirelessPlan.getName() + "</h3>");
			pw.print("<strong>" + WirelessPlan.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/wirelessplans/"
					+ WirelessPlan.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='wirelessplans'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='wirelessplans'>"+
					"<input type='hidden' name='price' value='"+WirelessPlan.getPrice()+"'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='wirelessplans'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div><div class='clear'></div>");
		utility.printHtml("Footer.html");
	}
}
