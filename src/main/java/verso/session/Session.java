package verso.session;

import verso.mapper.MappedStatement;

public interface Session {

	void rollback();
	void commit();
	void close();
	
	public <T> T getDao(Class<T> clazz);

	Object select(MappedStatement mappedStmt, Object[] args)
			throws Exception;
	
	Object other(MappedStatement mappedStmt, Object[] args)
	        throws Exception;
}
