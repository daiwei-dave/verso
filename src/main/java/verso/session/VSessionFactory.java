package verso.session;

import javax.sql.DataSource;

import verso.config.XMLConfigBuilder;

public class VSessionFactory implements SessionFactory {

    private DataSource config;
	
	public VSessionFactory(String resource) {
		config = XMLConfigBuilder.build(resource);
	}
	
	public VSession openSession() {
		return new VSession(config);
	}
}
