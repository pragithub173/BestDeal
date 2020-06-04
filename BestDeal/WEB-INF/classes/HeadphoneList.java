import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Headphone")

public class HeadphoneList extends HttpServlet {

	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Headphone> hm = new HashMap<String, Headphone>();

		if (CategoryName == null)	
		{
			hm.putAll(SaxParserDataStore.headphones);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("panasonic")) 
			{	
				for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Panasonic"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Panasonic";
			} 
			else if (CategoryName.equals("sony"))
			{
				for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Sony"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Sony";
			} 
			else if (CategoryName.equals("betron")) 
			{
				for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Betron"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Betron";
			}
			else if (CategoryName.equals("bose"))
			{
				for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Bose"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Bose";
			} 
			else if (CategoryName.equals("sennheiser"))
			{
				for(Map.Entry<String,Headphone> entry : SaxParserDataStore.headphones.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Sennheiser"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Sennheiser";
			} 
	    }

		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name +" Headphone's</a>"); 
		pw.print("</h2><div class='entry'><table id='bestseller'>"); 
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Headphone> entry : hm.entrySet()) {
			Headphone Headphone = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + Headphone.getName() + "</h3>");
			pw.print("<strong>" + Headphone.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/headphones/"
					+ Headphone.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='price' value='"+Headphone.getPrice()+"'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='headphones'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div><div class='clear'></div>");
		utility.printHtml("Footer.html");
	}
}
