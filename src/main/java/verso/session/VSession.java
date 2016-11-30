package verso.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.ResourceHolderSupport;

import verso.config.Environment;
import verso.mapper.MappedProxy;
import verso.mapper.MappedResult;
import verso.mapper.MappedStatement;

public class VSession extends ResourceHolderSupport implements Session {
	private Environment config;
	private Connection conn = null;

	@Override
	public <T> T getDao(Class<T> clazz) {
	    return MappedProxy.newInstance(clazz, this);
	}
	
	
	public VSession(Environment config) {
		this.config = config;
	}
	
	@Override
	public void rollback() {
	    System.out.println("rollback");
		try {
		    if (conn != null) conn.rollback();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	@Override
	public void commit() {
	    System.out.println("commit");
		try {
		    if (conn != null) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
	    System.out.println("close");
	    if (conn != null) {
	        DataSourceUtils.releaseConnection(conn, config.getDataSource());
	    }
	}

	@Override
	public Object select(MappedStatement mappedStmt, Object[] args)
			throws Exception {
		// 获取配置好参数的sql
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = mappedStmt.createStatement(getConnection(), args);
			rs = stmt.executeQuery();
			// 奖ResultSet转为注解指定的resultType类型，存入函数的返回值returnType类型
			String resultType = mappedStmt.getResultType();
			Class<?> returnType = mappedStmt.getReturnType();
			MappedResult mapper = config.getResult(resultType);
			return mapper.getResult(rs, returnType);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
		}
	}

	@Override
	public Object other(MappedStatement mappedStmt, Object[] args)
			throws Exception 
	{
		PreparedStatement stmt = null;
		try {
			stmt = mappedStmt.createStatement(getConnection(), args);
			return stmt.executeUpdate();
		} finally {
			if (stmt != null) stmt.close();
		}
	}
	
	private Connection getConnection() throws SQLException {
	    if (conn == null) {
	        System.out.println("open connection");
	        DataSource data = config.getDataSource();
	        conn = DataSourceUtils.getConnection(data);
	        conn.setAutoCommit(false);
	    }
        return conn;	    
	}
}
