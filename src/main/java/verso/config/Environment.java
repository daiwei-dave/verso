package verso.config;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	
	private DataSource data;
	private Map<String, Class<?>> daoMap = new HashMap<>();
	private Map<String, ResultMapper> resultMap = new HashMap<>();
    
	public void putDao(String className, Class<?> clazz) {
		daoMap.put(className, clazz);
	}
	public Class<?> getDao(String className) {
		return daoMap.get(className);
	}
	public void putResult(String key, ResultMapper value) {
		resultMap.put(key, value);
	}
	public ResultMapper getResult(String key) {
		return resultMap.get(key);
	}
	public DataSource getDataSource() {
		return data;
	}
	public void setDataSource(DataSource dataSource) {
		this.data = dataSource;
	}
}
