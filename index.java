import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

public class dbproducts2 {
   static final String DB_URL = "jdbc:mysql://localhost:3306/nht";
   static final String USER ="root";
   static final String PASS = "";
   static final String QUERY = "SELECT ProductID, ProdName, ProdBrand, Price,ProdDesc,quantity FROM products1";
   

   public static void printResultSet(ResultSet rs) throws SQLException{
      // Ensure we start with first row
      rs.beforeFirst();
      while(rs.next()){
         // Display values
         System.out.print("\nID: " + rs.getInt("ProductID"));
         System.out.print("\nname: " + rs.getString("ProdName"));
         System.out.print("\nProdBrand: " + rs.getString("ProdBrand"));
         System.out.println(", price: " + rs.getString("Price"));
         System.out.print("\nDescription: " + rs.getString("ProdDesc"));
         System.out.print("\nQuantity: " + rs.getString("quantity"));
      }
      System.out.println();
   }
  
      public static void main(String[] args)throws IOException,FileNotFoundException,SQLException,NoSuchElementException {
      // Open a connection
      String ch;
      String id1="";
      String name1="";
      String desc1="";
      String brand1="";
      String price1="";
      String quantity1="";
      Scanner sc=new Scanner(System.in);
      Scanner scanner1=new Scanner(new File("Products.txt"));

      FileWriter writer=null;
      scanner1.useDelimiter("-|\n");
      String s;
      String[]name;
      String[]desc;
      String[]type;
      String[]brand;
      String[]id;
      String[]q;
      String[]price;
      String content, keys;
      //FileWriter fw = null; 
      BufferedWriter bw = null; 
      PrintWriter pw = null;

  LinkedHashSet<String> set = new LinkedHashSet<String>();

      boolean f=false;
      boolean flag=false;
      CheckingDuplicates cd=new CheckingDuplicates();
          String item;
          List<String> asList; 
       
          System.out.println("Enter item to be searched:");
          item=sc.next();
          searchcolumns src=new searchcolumns();
           String[] keyValue;
           PreparedStatement ps1=null; 
           String query="SELECT * FROM products1 WHERE ProdName LIKE ? OR ProductID LIKE ? OR ProdDesc LIKE ?"+
           " OR ProdBrand LIKE ? OR Price LIKE ? OR quantity LIKE ?";
      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement(
         ResultSet.TYPE_SCROLL_INSENSITIVE,
         ResultSet.CONCUR_UPDATABLE);
         PreparedStatement ps = conn.prepareStatement("TRUNCATE products1");
         
         
         ResultSet rs = stmt.executeQuery(QUERY);)
       {  

         rs.beforeFirst();
         ps.executeUpdate();

         try{
      
           writer = new FileWriter("DatabaseResults.txt", true); 
           bw = new BufferedWriter(writer); 
           pw = new PrintWriter(bw);

         while((s=scanner1.next())!=null)
           {
            keyValue = s.split(",");
             
               id = keyValue[0].trim().split(",");
               name = keyValue[1].trim().split(",");
               desc = keyValue[2].trim().split(",");
               price = keyValue[3].trim().split(",");
               brand = keyValue[4].trim().split(",");
               type = keyValue[5].trim().split(",");
               q = keyValue[6].trim().split(",");
              cd.check(rs,id,name,desc,brand,price,q);
            

            ps1=src.searching(conn,query,item);
            ResultSet rs1;
            rs1 = ps1.executeQuery();
 
            // where Condition check
            
            while (rs1.next()) {
              //---------------Getting results from query---------------
               id1 = rs1.getString("ProductID");
                 name1 = rs1.getString("ProdName");
                 desc1 = rs1.getString("ProdDesc");
                 brand1 = rs1.getString("ProdBrand");
                 price1 = rs1.getString("Price");
                quantity1 = rs1.getString("quantity");

   //----------------Adding it to the hashset------------------------------------
                 set.add("Product ID:"+id1);
                 set.add("Product Name:"+name1);
                  set.add("Product Description:"+desc1);
                  set.add("Product Price:"+ price1);
                  set.add("Brand:"+brand1);
                  set.add("Quantity:"+quantity1);
                  
                 f=true;
               
               }
       }
       }
     catch(NoSuchElementException e){
    // writer.write("Here are some search results:-\n");
     writer.write("\n-----------The results for "+ item+" are--------------\n");
     if(f==true){
        writer=new FileWriter("DatabaseResults.txt", true);
        System.out.println("---------The results are:--------\n");
        asList= new ArrayList<String>(set);
        System.out.println("\n"+asList);
        for(String str:asList){
         pw.println(str);
      }
      pw.println("TimeStamp:" + LocalDateTime.now() + "\n");
     }
     else{
        System.out.println("No records found");
         writer.write("No records found!!");
     }
     System.out.println("Done.");
  }
  finally {
             if (writer != null) {
                 writer.flush();
                 writer.close();
                 
                 pw.close();
                 bw.close();
             }
         }
        
     }
  }
  }
