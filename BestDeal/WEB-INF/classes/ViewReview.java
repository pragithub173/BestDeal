import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {           
                response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view Review");
			response.sendRedirect("Login");
			return;
		}
		 String productName=request.getParameter("name");		 
		HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
		String userName = "";
		String reviewRating = "";
		String reviewDate;
		String reviewText = "";	
		String price = "";
		String city ="";
		String retailername = "";
		String retailerstate="";
		String productsale = "";
		String manufacturerebate = "";
		String userid = "";
		String userage = "";
		String usergender = "";
		String useroccupation = "";
                utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
	
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Review</a>");
		pw.print("</h2><div class='entry'>");
			
			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
		if(hm==null)
		{
		pw.println("<h2>Mongo Db server is not up and running</h2>");
		}
		else
		{
                if(!hm.containsKey(productName)){
				pw.println("<h2>There are no reviews for this product.</h2>");
			}else{
		for (Review r : hm.get(productName)) 
				 {		
		pw.print("<table class='gridtable'>");
				pw.print("<tr>");
				pw.print("<td> Product Name: </td>");
				productName = r.getProductName();
				pw.print("<td>" +productName+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td> userName: </td>");
				userName = r.getUserName();
				pw.print("<td>" +userName+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td> price: </td>");
				price = r.getPrice();
				pw.print("<td>" +price+ "</td>");
				pw.print("</tr>");
				pw.print("<tr>");
				pw.print("<td> Retailer City: </td>");
				city = r.getRetailerCity();
				pw.print("<td>" +city+ "</td>");
				pw.print("</tr>");
				pw.println("<tr>");
				pw.println("<td> Review Rating: </td>");
				reviewRating = r.getReviewRating().toString();
				pw.print("<td>" +reviewRating+ "</td>");
				pw.print("</tr>");
				//--------------------------------------
				pw.println("<tr>");
				pw.println("<td> Retailer Name </td>");
				retailername = r.getretailername().toString();
				pw.print("<td>" +retailername+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td> Retailer State </td>");
				retailerstate = r.getretailerstate().toString();
				pw.print("<td>" +retailerstate+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>Manufacture rebate </td>");
				manufacturerebate = r.getmanufacturerebate().toString();
				pw.print("<td>" +manufacturerebate+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>Product on Sale</td>");
				productsale = r.getproductsale().toString();
				pw.print("<td>" +productsale+"</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>User ID </td>");
				userid = r.getuserid().toString();
				pw.print("<td>" +userid+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>User Age </td>");
				userage = r.getuserage().toString();
				pw.print("<td>" +userage+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>User Gender </td>");
				usergender = r.getusergender().toString();
				pw.print("<td>" +usergender+ "</td>");
				pw.print("</tr>");

				pw.println("<tr>");
				pw.println("<td>User Occupation </td>");
				useroccupation = r.getuseroccupation().toString();
				pw.print("<td>" +useroccupation+ "</td>");
				pw.print("</tr>");
				//--------------------------------------
				pw.print("<tr>");
				pw.print("<td> Review Date: </td>");
				reviewDate = r.getReviewDate().toString();
				pw.print("<td>" +reviewDate+ "</td>");
				pw.print("</tr>");			
				pw.print("<tr>");
				pw.print("<td> Review Text: </td>");
				reviewText = r.getReviewText();
				pw.print("<td>" +reviewText+ "</td>");
				pw.print("</tr>");
				pw.println("</table>");
				}					
							
		}
		}	       
                pw.print("</div></div></div><div class='clear'></div>");		
		utility.printHtml("Footer.html");
	                     	
                    }
              	catch(Exception e)
		{
                 System.out.println(e.getMessage());
		}  			
       
	 	
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}