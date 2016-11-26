package verso.mapper;

import java.lang.reflect.Method;

import verso.session.VSession;

public class MappedMethod 
{
	Method method;
	MappedStatement stmt;

	public MappedMethod(Method method) throws Exception {
		this.method = method;
		this.stmt = MappedStatement.getInstance(method);
	}
	
	public Object invoke(Object args[], VSession session) throws Exception {
		//System.out.println("invoke : " + anno.sql());
		//System.out.println("args : " + Arrays.toString(args));

		//System.out.println(sqlType);
		switch (stmt.getSqlType()) {
		case INSERT: case UPDATE: case DELETE:
			return session.other(stmt, args);
		case SELECT:
			return session.select(stmt, args);
		case FLUSH:
			session.test();
			//result = sqlSession.flushStatements();
			break;
		default:
			
		}
		return null;
	}
}
