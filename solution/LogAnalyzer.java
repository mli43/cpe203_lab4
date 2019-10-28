import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class LogAnalyzer
{
      //constants to be used when pulling data out of input
      //leave these here and refer to them to pull out values
   private static final String START_TAG = "START";
   private static final int START_NUM_FIELDS = 3;
   private static final int START_SESSION_ID = 1;
   private static final int START_CUSTOMER_ID = 2;
   private static final String BUY_TAG = "BUY";
   private static final int BUY_NUM_FIELDS = 5;
   private static final int BUY_SESSION_ID = 1;
   private static final int BUY_PRODUCT_ID = 2;
   private static final int BUY_PRICE = 3;
   private static final int BUY_QUANTITY = 4;
   private static final String VIEW_TAG = "VIEW";
   private static final int VIEW_NUM_FIELDS = 4;
   private static final int VIEW_SESSION_ID = 1;
   private static final int VIEW_PRODUCT_ID = 2;
   private static final int VIEW_PRICE = 3;
   private static final String END_TAG = "END";
   private static final int END_NUM_FIELDS = 2;
   private static final int END_SESSION_ID = 1;

      //a good example of what you will need to do next
      //creates a map of sessions to customer ids
   
   private static void processStartEntry(
      final String[] words,
      final Map<String, List<String>> sessionsFromCustomer)
   {
      if (words.length != START_NUM_FIELDS)
      {
         return;
      }
      
      

         //check if there already is a list entry in the map
         //for this customer, if not create one
      
      List<String> sessions = sessionsFromCustomer
         .get(words[START_CUSTOMER_ID]);
      if (sessions == null)
      {
         sessions = new LinkedList<>();
         sessionsFromCustomer.put(words[START_CUSTOMER_ID], sessions);
      }

         //now that we know there is a list, add the current session
      sessions.add(words[START_SESSION_ID]);
   }

   
   
   
   
      //similar to processStartEntry, should store relevant view
      //data in a map - model on processStartEntry, but store
      //your data to represent a view in the map (not a list of strings)
   private static void processViewEntry(
		   final String[] words,
		   final Map<String, List<View>> viewsFromSession)
   {
	   if (words.length != VIEW_NUM_FIELDS)
	      {
	         return;
	      }
	      
	      // words: ["VIEW", "session2", "product1", "100"]

	         //check if there already is a list entry in the map
	         //for this customer, if not create one
	   
	   
	   /*
	   viewsFromSession.putIfAbsent(words[VIEW_SESSION_ID], new LinkedList<>());
	   
	   String sessionID = words[VIEW_SESSION_ID];
	      
	   String productID = words[VIEW_PRODUCT_ID];
	   
	   int productPrice = Integer.parseInt(words[VIEW_PRICE]);
	   
	   View view = new View(productID, productPrice);
	   
	   viewsFromSession.get(sessionID).add(view);
	   */
	   
	   
	   List<View> views = viewsFromSession
		         .get(words[VIEW_SESSION_ID]);
		      if (views == null)
		      {
		         views = new LinkedList<View>();
		         viewsFromSession.put(words[VIEW_SESSION_ID], views);
		      }

		      int productPrice = Integer.parseInt(words[VIEW_PRICE]);
		      
		      String productID = words[VIEW_PRODUCT_ID];
		      
		      View view = new View(productID, productPrice);
		      
		      views.add(view);
	 
	   
	   
   }
   
   
   
   

      //similar to processStartEntry, should store relevant purchases
      //data in a map - model on processStartEntry, but store
      //your data to represent a purchase in the map (not a list of strings)
   private static void processBuyEntry(
		   final String[] words, 
		   final Map<String, List<Buy>> buysFromSession)
   {
	   if (words.length != BUY_NUM_FIELDS)
	      {
	         return;
	      }
	   
	   /*
	   buysFromSession.putIfAbsent(words[BUY_SESSION_ID], new LinkedList<>());
	   
	   String sessionID = words[BUY_SESSION_ID];
	      
	   String productID = words[BUY_PRODUCT_ID];
	   
	   int productPrice = Integer.parseInt(words[BUY_PRICE]);
	   
	   int productQuantity = Integer.parseInt(words[BUY_QUANTITY]);
	   
	   Buy buy = new Buy(productID, productPrice, productQuantity);
	   
	   buysFromSession.get(sessionID).add(buy);
	   */
	   
	   
	   
	   
	   List<Buy> buys = buysFromSession
		         .get(words[BUY_SESSION_ID]);
		      if (buys == null)
		      {
		         buys = new LinkedList<Buy>();
		         buysFromSession.put(words[BUY_SESSION_ID], buys);
		      }

		      int productPrice = Integer.parseInt(words[BUY_PRICE]);
		      
		      int productQuantity = Integer.parseInt(words[BUY_QUANTITY]);
		      
		      String productID = words[BUY_PRODUCT_ID];
		      
		      Buy buy = new Buy(productID, productPrice, productQuantity);
		      
		      buys.add(buy);
		      
		      
		      
	   
	   
	   
	   
   }
   
   
   

   private static void processEndEntry(final String[] words)
   {
      if (words.length != END_NUM_FIELDS)
      {
         return;
      }
   }

   
   
   
      //this is called by processFile below - its main purpose is
      //to process the data using the methods you write above
   private static void processLine(
      final String line,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      final String[] words = line.split("\\h");

      if (words.length == 0)
      {
         return;
      }

      switch (words[0])
      {
         case START_TAG:
            processStartEntry(words, sessionsFromCustomer);
            break;
         case VIEW_TAG:
            processViewEntry(words, viewsFromSession);
            break;
         case BUY_TAG:
            processBuyEntry(words, buysFromSession);
            break;
         case END_TAG:
            processEndEntry(words);
            break;
      }
   }
   
   
   
   
   
   
   private static void AverageViewsNoPurchase(
		   final Map<String, List<String>> sessionsFromCustomer,
		   final Map<String, List<View>> viewsFromSession,
		   final Map<String, List<Buy>> buysFromSession)
   
   {
	   
	   int totalViews = 0;
	   double totalSessions = 0.0;
	   
	   
	   List<String> custList = new LinkedList<String> (sessionsFromCustomer.keySet());
	   for (String cust : custList) {
		   List<String> sessionList = sessionsFromCustomer.get(cust);
		   
		   
		   for(String sessionId : sessionList) {
			   List<Buy> buys = buysFromSession.get(sessionId);
			   
			   if(buys == null) {
				   totalSessions ++;
				   
				   
				   List<View> views = viewsFromSession.get(sessionId);
				   if(views != null) {
					   totalViews += views.size();
				   }
			   }
		   }
	   }
	   
	   double result = totalViews/ totalSessions;
	   System.out.println("Average Views without Purchase: " + result);

   }
   
   
   
   
   
   
   
   
   
   

   private static void printSessionPriceDifference(
		   final Map<String, List<View>> viewsFromSession,
		   final Map<String, List<Buy>> buysFromSession)
   {
      System.out.println("Price Difference for Purchased Product by Session");
      


      /* add printing */
      
      
      List<String> sessionList = new LinkedList<String> (viewsFromSession.keySet());
	   for (String session : sessionList) {
		   
		  
		   List<Buy> buys = buysFromSession.get(session);
		   
		   
		   if(buys != null) {
			   List<View> viewList = viewsFromSession.get(session);
			   
			   
			   double totalPrice = 0.0;
			   int totalQuantity = 0;
			   
			   for(View view : viewList) {
				   totalPrice += view.getPrice();
				   totalQuantity += 1;
				   
			   }
			   
			   for(Buy buy : buys) {
				   double finalPrice = buy.getPrice() - totalPrice/totalQuantity;
				   
				   System.out.println(session + "   " + buy.getProduct() + " " + finalPrice);
			   }
			   
			   
		   }
		   
		   
	
	   }
      
   }
   
   
   

      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printCustomerItemViewsForPurchase(
		   final Map<String, List<String>> sessionsFromCustomer,
		   final Map<String, List<View>> viewsFromSession,
		   final Map<String, List<Buy>> buysFromSession
		   )
   {
      System.out.println("Number of Views for Purchased Product by Customer");

      /* add printing */
      
      
      List<String> custSessions = new LinkedList<String> (sessionsFromCustomer.keySet());
      for(String custSession : custSessions) {
    	  List<Buy> buyQuantity = new LinkedList<> ();
    	  List<String> custSessions2 = sessionsFromCustomer.get(custSession);
    	  
    	  
    	  for(String custSession2 : custSessions2) {
    		  List<Buy> buySession = buysFromSession.get(custSession2);
    		  
    		  
    		  if(buySession != null) {
    			  for(Buy buy : buySession) {
    				  buyQuantity.add(buy);
    			  }
    			  
    		  }
    		  
    	  }
    	  
    	  if(buyQuantity.size() != 0) {
    		  System.out.println("   " + custSession);
    		  
    		  for(Buy buy2 : buyQuantity) {
    			  int quan = 0;
    			  String productID = buy2.getProduct();
    			  
    			  for(String session : custSessions2) {
    				  List<View> viewList = viewsFromSession.get(session);
    				  
    				  
    				  if(viewList != null) {
    					  for(View view : viewList) {
    						  if(view.getProduct().equals(productID)) {
    							  
    							  quan += 1;
    							  break;
    							  
    						  }
    					  }
    				  }
    			  }
    			  
    			  System.out.println("   " + productID + " " + quan);
    		  }
    		  
    		  
    	  }
    	  
      }
      
      
      
      
      
      
      
   }

   
   
      //write this after you have figured out how to store your data
      //make sure that you understand the problem
   private static void printStatistics(
		   final Map<String, List<String>> sessionsFromCustomer,
		   final Map<String, List<View>> viewsFromSession,
		   final Map<String, List<Buy>> buysFromSession)
   {
	  AverageViewsNoPurchase(sessionsFromCustomer, viewsFromSession, buysFromSession);
	  printSessionPriceDifference(viewsFromSession, buysFromSession);
      printCustomerItemViewsForPurchase(sessionsFromCustomer, viewsFromSession, buysFromSession);

      /* This is commented out as it will not work until you read
         in your data to appropriate data structures, but is included
         to help guide your work - it is an example of printing the
         data once propogated 
         printOutExample(sessionsFromCustomer, viewsFromSession, buysFromSession);
      */
   }

   /* provided as an example of a method that might traverse your
      collections of data once they are written 
      commented out as the classes do not exist yet - write them! */

   private static void printOutExample(
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession) 
   {
      //for each customer, get their sessions
      //for each session compute views
      for(Map.Entry<String, List<String>> entry: 
         sessionsFromCustomer.entrySet()) 
      {
         System.out.println(entry.getKey());
         List<String> sessions = entry.getValue();
         for(String sessionID : sessions)
         {
            System.out.println("\tin " + sessionID);
            List<View> theViews = viewsFromSession.get(sessionID);
            for (View thisView: theViews)
            {
               System.out.println("\t\tviewed " + thisView.getProduct());
            }
         }
      }
   }


      //called in populateDataStructures
   private static void processFile(
      final Scanner input,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession
      /* add parameters as needed */
      )
   {
      while (input.hasNextLine())
      {
         processLine(input.nextLine(), sessionsFromCustomer, viewsFromSession, buysFromSession);
      }
   }

      //called from main - mostly just pass through important data structures
   private static void populateDataStructures(
      final String filename,
      final Map<String, List<String>> sessionsFromCustomer,
      final Map<String, List<View>> viewsFromSession,
      final Map<String, List<Buy>> buysFromSession)
   
      throws FileNotFoundException
   {
      try (Scanner input = new Scanner(new File(filename)))
      {
         processFile(input, sessionsFromCustomer, viewsFromSession, buysFromSession
            /* add arguments as needed */ );
      }
   }

   private static String getFilename(String[] args)
   {
      if (args.length < 1)
      {
         System.err.println("Log file not specified.");
         System.exit(1);
      }

      return args[0];
   }

   public static void main(String[] args)
   {
      /* Map from a customer id to a list of session ids associated with
       * that customer.
       */
      final Map<String, List<String>> sessionsFromCustomer = new HashMap<>();
      final Map<String, List<View>> viewsFromSession = new HashMap<>();
      final Map<String, List<Buy>> buysFromSession = new HashMap<>();

      /* create additional data structures to hold relevant information */
      /* they will most likely be maps to important data in the logs */

      final String filename = getFilename(args);

      try
      {
    	 populateDataStructures(filename, sessionsFromCustomer, viewsFromSession, buysFromSession);
         printStatistics(sessionsFromCustomer, viewsFromSession, buysFromSession);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }
}
