import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class searchcolumns {

    public PreparedStatement searching(Connection conn,String query,String item) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement(query);
        ps.setObject(1,"%"+item+"%");
        ps.setObject(2, "%"+item+"%");
        ps.setObject(3,"%"+item+"%");
        ps.setObject(4,"%"+item+"%");
        ps.setObject(5,"%"+item+"%");
        ps.setObject(6,"%"+item+"%");
        ps.execute();
        return ps;
    }
}
