import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Voice")

public class VoiceList extends HttpServlet {

	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String,Voice> allvoices = new HashMap<String,Voice> ();
        try{
		     allvoices = MySqlDataStoreUtilities.getVoices();
		}
		catch(Exception e)
		{
			
		}

		/* Checks the Tablets type whether it is microsft or sony or nintendo */

		HashMap<String, Voice> hm = new HashMap<String, Voice>();
		if(CategoryName==null){
			hm.putAll(allvoices);
			name = "";
		}
		else 
		{
			if(CategoryName.equals("amazon")) 
			{	
				for(Map.Entry<String,Voice> entry : allvoices.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Amazon"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Amazon";
			} 
			else if (CategoryName.equals("apple"))
			{
				for(Map.Entry<String,Voice> entry : allvoices.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Apple"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Apple";
			} 
			else if (CategoryName.equals("google")) 
			{
				for(Map.Entry<String,Voice> entry : allvoices.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Google"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Google";
			}
	    }

		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name +" Voice Assistant's</a>"); 
		pw.print("</h2><div class='entry'><table id='bestseller'>"); 
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Voice> entry : hm.entrySet()) {
			Voice Voice = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + Voice.getName() + "</h3>");
			pw.print("<strong>" + Voice.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/voiceassistants/"
					+ Voice.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='voices'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='voices'>"+
					"<input type='hidden' name='price' value='"+Voice.getPrice()+"'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='voices'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
