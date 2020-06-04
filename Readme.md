The intent is to build servlet-based web application that will allow customers to place prders online from BestDeal website.
The website has 9 product categories.The products are hardcoded and are stored in HashMap or xml file.
The store has StoreManager, Customer and Salesman and the website allows all the 3 to login to the site.
Salesman can create customer accounts and can add/delete/update customer's orders.
Each and every product on the website has a Name and Price. When the products is selected for the view all the accessories associated with that products are displayed.

Customers will be allowed to create a account, view multiple products, add products to cart, place orders, check the status of the order or cancel the order online.
Customer will be able to enter personal information such as Name, Address and Credit card details befor placing the order and he will be provided a conformation.

All the account login information are stored in SQL database i.e.,MySQL.
All the customer transactions/orders are stored in SQL database i.e.,MySQL.
All order updates to insert/delete/update orders are reflected in the MySQL database.
Customer will be able to submit product reviews that are stored in database.
Product reviews are stored in NoSQL database i.e., MongoDB. Add Trending & Data Analytics feature (detailed below).

The Store Manager will be able to see the table of product inventory, bar chart of inventory, table of all products currently on sale, table of all products currently that have manufacture rebates, table of products sold, bar chart of products sold, daily sale transactions and data visualization of products sold based on zip code.


Search Auto-Completion feature is added in the following manner.
Auto-complete-feature is implemented as follows: 
When the app-server starts up, the Products are first read into a hashmap from ProductCatalog.xml file and then stored in MySQL database.
Since the store manager can insert/update/delete products, all of these operations are reflected in the hashmap and then MySQL database.

All new code added for the auto-complete-complete feature shall be placed in a class called AjaxUtility.java
write this in readme file.


Deal Match feature

The python script will connect to Twitter server in order to get the current deals of BestBuy under the screen_name BestBuy_Deals.
The python script will connect to MySQL server for SmartPortables app in order to get the list of products.
The python script will compare the product names retrieved from MySQL server to the products names retrieved from screen_name BestBuy_Deals on Twitter server and it will write the tweets that have the products names that match the product names retrieved from MySQL server in the product table into DealMatches.txt file.
When Tomcat server is started, it will read ANY 2 lines from DealMatches.txt file and display them on homepage along with links to the individual products that website can match of the offered/displayed deals by BestBuy on the homepage of BestDeal website. 
All new code added for this feature shall be placed in a class called DealMacthes.java


Recommender Feature

Based on the history of the user purchases and after the user makes a purchase, the enterprise web application makes three product recommendations for the logged in user.


How to deploy and run

1.	Start MySQL server
2.	Run the server monogd.exe
3.	Set the following environment paths in the command prompt:

	set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_221

	set PATH="C:\Program Files\Java\jdk1.8.0_221\bin";%PATH%
	
	set CLASSPATH=C:\apache-tomcat-7.0.34\lib\servlet-api.jar;C:\apache-tomcat-7.0.34\lib\jsp-api.jar;C:\apache-tomcat-7.0.34\lib\el-api.jar;C:\apache-tomcat-7.0.34\lib\commons-beanutils-1.8.3.jar;C:\apache-tomcat-7.0.34\lib\mysql-connector-java-8.0.17.jar;C:\apache-tomcat-7.0.34\lib\mongo-java-driver-3.2.2.jar;C:\apache-tomcat-7.0.34\lib\gson-2.6.2.jar

	set ANT_HOME=C:\apache-tomcat-7.0.34

	set TOMCAT_HOME=C:\apache-tomcat-7.0.34

	set CATALINA_HOME=C:\apache-tomcat-7.0.34 
4. 	Compile the source code
5.	Start Apache Tomcat server
6.	Now run the application in the browser using the following command: localhost/BestDeal




