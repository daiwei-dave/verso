package verso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class VSession implements Session {

    private DataSource dataSource;
    private Connection conn;
    
    public VSession(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = dataSource.getConnection();
        }
        return conn;
    }
    
    @Override
    public <T> T select(String sql, Class<T> mapperResult, Object... arg) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
		    stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (rs.next()) {
			    for (int i=1; i<=rsmd.getColumnCount(); i++) {
			        System.out.println(rsmd.getColumnLabel(i)+"="+rs.getObject(i));
			    }
			}
			return null;
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
		}
    }
    
}
