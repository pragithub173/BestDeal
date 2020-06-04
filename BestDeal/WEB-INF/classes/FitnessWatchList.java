import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FitnessWatch")

public class FitnessWatchList extends HttpServlet {

	

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, FitnessWatch> hm = new HashMap<String, FitnessWatch>();

		if (CategoryName == null)	
		{
			hm.putAll(SaxParserDataStore.fitnesswatches);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("letsfit")) 
			{	
				for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Letsfit"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Letsfit";
			} 
			else if (CategoryName.equals("fitbit"))
			{
				for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Fitbit"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Fitbit";
			} 
			else if (CategoryName.equals("willful")) 
			{
				for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Willful"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Willful";
			}
			else if (CategoryName.equals("bingofit"))
			{
				for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("BingoFit"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "BingoFit";
			} 
			else if (CategoryName.equals("pubu"))
			{
				for(Map.Entry<String,FitnessWatch> entry : SaxParserDataStore.fitnesswatches.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("PUBU"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "PUBU";
			} 
	    }

		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name +" Fitness Watches</a>"); 
		pw.print("</h2><div class='entry'><table id='bestseller'>"); 
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, FitnessWatch> entry : hm.entrySet()) {
			FitnessWatch FitnessWatch = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + FitnessWatch.getName() + "</h3>");
			pw.print("<strong>" + FitnessWatch.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/fitnesswatches/"
					+ FitnessWatch.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='fitnesswatches'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='fitnesswatches'>"+
					"<input type='hidden' name='price' value='"+FitnessWatch.getPrice()+"'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='fitnesswatches'>"+
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
