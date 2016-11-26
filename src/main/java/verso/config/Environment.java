package verso.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import verso.mapper.MappedProxy;
import verso.mapper.MappedResult;
import verso.mapper.impl.MappedPrimitiveResult;

/**
 * 存有配置信息中的dao方法与resultMap（同MyBatis）以及DataSource
 * @author tjg
 */
public class Environment {
	
	private DataSource data;
	private Map<String, MappedResult> resultMap = new HashMap<>();

	public Environment() {
		MappedResult primResult = new MappedPrimitiveResult(); 
		resultMap.put("string", primResult);
		resultMap.put("byte", primResult);
		resultMap.put("short", primResult);
		resultMap.put("int", primResult);
		resultMap.put("long", primResult);
		resultMap.put("double", primResult);
		resultMap.put("float", primResult);
		resultMap.put("bool", primResult);
		resultMap.put("char", primResult);
		resultMap.put("", primResult);
	}
	
	public void putResult(String key, MappedResult value) {
		resultMap.put(key, value);
	}
	public MappedResult getResult(String key) {
		return resultMap.get(key);
	}
	public DataSource getDataSource() {
		return data;
	}
	public void setDataSource(DataSource dataSource) {
		this.data = dataSource;
	}
}
