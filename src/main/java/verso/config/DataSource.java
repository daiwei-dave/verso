package verso.config;

/**
 * 数据库信息
 * @author tjg
 */
public class DataSource {
    String url;
    String driverClassName;
	String username;
    String password;
    
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
    	try {
    		Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			System.err.println("can't find jdbc " + driverClassName);
		}
		this.driverClassName = driverClassName;
	}
}
