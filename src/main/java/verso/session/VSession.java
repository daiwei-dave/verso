package verso.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import verso.config.Environment;
import verso.mapper.MappedProxy;
import verso.mapper.MappedResult;
import verso.mapper.MappedStatement;

public class VSession {
	private Environment config;

	public <T> T getDao(Class<T> clazz) {
	    return MappedProxy.newInstance(clazz, this);
	}
	
	private Connection conn = null;
	
	public VSession(Environment config) {
		this.config = config;
		DataSource data = config.getDataSource();
		while (true) {
			try {
				conn = data.getConnection();
				if (conn == null) {
					throw new SQLException();
				} else {
					conn.setAutoCommit(false);// 禁止自动提交，设置回滚点
					break;	//成功则结束循环
				}
			} catch (SQLException e) {
				try {
					//出现异常，睡一定时间后重连数据库
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}

	public void beginTransaction() {
        System.out.println("beginTransaction");
        try {
            conn = config.getDataSource().getConnection();
            conn.setAutoCommit(false);// 禁止自动提交，设置回滚点
        } catch (SQLException e) {
            e.printStackTrace();
        }  
	}
	
	public void rollback() {
	    System.out.println("rollback");
		try {
		    if (conn != null) conn.rollback();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public void commit() {
	    System.out.println("commit");
		try {
		    if (conn != null) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object select(MappedStatement mappedStmt, Object[] args)
			throws Exception {
		// 获取配置好参数的sql
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = mappedStmt.createStatement(conn, args);
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

	public Object other(MappedStatement mappedStmt, Object[] args)
			throws Exception 
	{
		PreparedStatement stmt = null;
		try {
			stmt = mappedStmt.createStatement(conn, args);
			return stmt.executeUpdate();
		} finally {
			if (stmt != null) stmt.close();
		}
		
	}

	public void test() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select p.id as author, p.name from book left join person p on book.author=p.id");
			ResultSetMetaData rsmd = rs.getMetaData();
			/*
			 * column name 表上的列名 column label 表上的列名或as的别名
			 */
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnLabel(i);
					System.out.println(name);
				}
				System.out.println("-----------------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
