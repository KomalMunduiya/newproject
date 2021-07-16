import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckingDuplicates {


    public void check(ResultSet rs,String[] id,String[] name, String[] desc,String[] brand, String[] price,String[] quantity) throws SQLException
    {
        //PreparedStatement statement;
        Boolean flag=false;
       for (int i = 0; i < id.length; i++) {
                    if (!(id[i].toLowerCase().matches("ProductId")) || !(name[i].toLowerCase().matches("ProdName")) ||
                            !(desc[i].toLowerCase().matches("ProdDesc")) ||!(brand[i].toLowerCase().matches("ProdBrand")) ||
                            !(price[i].toLowerCase().matches("Price")) ||!(quantity[i].toLowerCase().matches("quantity")))
                            {
                            	rs.moveToInsertRow();
				                rs.updateObject("ProductID",id[i]);    
				                rs.updateObject("ProdName",name[i]);
				                rs.updateObject("ProdDesc",desc[i]);
				                rs.updateObject("ProdBrand",brand[i]);
				                rs.updateObject("Price",(price[i]));
				                rs.updateObject("quantity",(quantity[i]));
				                rs.insertRow();
                            }
                            
        
    }
    
	}
	
}
    //return flag;

