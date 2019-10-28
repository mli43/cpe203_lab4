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
    private static void processViewEntry(final String[] words,
                                         final Map<String, List<View>> viewsFromSession)
    /* add parameters as needed */

    {
        if (words.length != VIEW_NUM_FIELDS)
        {
            return;
        }

        List<View> views = viewsFromSession.get(words[VIEW_SESSION_ID]);
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
            final Map<String, List<Buy>> buysFromSession
    /* add parameters as needed */
      )
    {
        if (words.length != BUY_NUM_FIELDS)
        {
            return;
        }
        List<Buy> buys = buysFromSession.get(words[BUY_PRODUCT_ID]);
        if (buys == null){
            buys = new LinkedList<Buy>();
            buysFromSession.put(words[BUY_SESSION_ID], buys);
        }

        int productId = words[BUY_PRODUCT_ID];
        int productPrice = Integer.parseInt(words[BUY_PRICE]);
        int quantity = Integer.parseInt(words[BUY_QUANTITY]);

        buys.add(new Buy(productId, productPrice, quantity));
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


    private static void AvgViewsNoPurchase(
            final Map<String, List<String>> sessionsFromCustomer,
            final Map<String, List<View>> viewsFromSession,
            final Map<String, List<Buy>> buysFromSession)
    {
        int totalViews = 0;
        int totalNoPurchaseSessions = 0;
        List<String> customerList = new LinkList<String>();

        for(Map.Entry<String, List<String>> entry: sessionsFromCustomer.entrySet())
        {
            customerList.add(entry.getKey());
        }

        for(String sessionId : customerList)
        {
            List<Buy> buys = buysFromSession.get(sessionId);
            if (buys == null) //if no purchase
            {
                totalNoPurchaseSessions ++;
                List<View> views = viewsFromSession.get(sessionId);
                if (views != null) //if viewed
                {
                    totalViews += views.size();
                }
            }
        }

        double result = totalViews/totalNoPurchaseSessions;
        System.out.println("Average Views Without Purchase: " + result);
        System.out.println();
    }

    //write this after you have figured out how to store your data
    //make sure that you understand the problem
    private static void printSessionPriceDifference(
            final Map<String, List<View>> viewsFromSession,
            final Map<String, List<Buy>> buysFromSession)
    {
        /*for(Map.Entry<String, List<String>> entry:
                sessionsFromCustomer.entrySet())
        {
            System.out.println(entry.getKey());
            List<String> sessions = entry.getValue();*/
        System.out.println("Price Difference for Purchased Product by Session");
        double totalPrice = 0;

        for (Map.Entry<String, List<Buy>> entry: buysFromSession.entrySet()){
            String sessionId = entry.getKey(); //sessionId of map of buys
            List<Buy> buys = entry.getValue(); //list of buys of single session

            System.out.println(sessionId); //print out sessionId
            List<View> views = viewsFromSession.get(sessionId); //views of the sessionId

            for (View view: views){
                totalPrice += view.getPrice();
            }
            double averagePrice = totalPrice / views.size();
            for (Buy buy: buys) {
                System.out.println("    " + view.getProductId() + " " + buy.getPrice() - averagePrice);
            }
            totalPrice = 0;
        }

        /* add printing */
    }

    //write this after you have figured out how to store your data
    //make sure that you understand the problem
    private static void printCustomerItemViewsForPurchase(
            final Map<String, List<String>> sessionsFromCustomer,
            final Map<String, List<View>> viewsFromSession,
            final Map<String, List<Buy>> buysFromSession)
    {
        System.out.println("Number of Views for Purchased Product by Customer");

        /* add printing */
    }

    //write this after you have figured out how to store your data
    //make sure that you understand the problem
    private static void printStatistics(
            final Map<String, List<String>> sessionsFromCustomer,
            final Map<String, List<View>> viewsFromSession,
            final Map<String, List<Buy>> buysFromSession)
    {
        AvgViewsNoPurchase(sessionsFromCustomer, viewsFromSession, buysFromSession);
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
/*
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
*/

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
            processLine(input.nextLine(), sessionsFromCustomer, sessionsFromCustomer, viewsFromSession, buysFromSession
                    /* add arguments as needed */ );
        }
    }

    //called from main - mostly just pass through important data structures
    private static void populateDataStructures(
            final String filename,
            final Map<String, List<String>> sessionsFromCustomer,
            final Map<String, List<View>> viewsFromSession,
            final Map<String, List<Buy>> buysFromSession
            /* add parameters as needed */
    )
            throws FileNotFoundException
    {
        try (Scanner input = new Scanner(new File(filename)))
        {
            processFile(input, sessionsFromCustomer, sessionsFromCustomer, viewsFromSession, buysFromSession
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
            populateDataStructures(filename, sessionsFromCustomer, viewFromSession, buysFromSession
                    /* add parameters as needed */
            );
            printStatistics(sessionsFromCustomer, viewFromSession, buysFromSession
                    /* add parameters as needed */
            );
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
