package verso.session;

import java.io.IOException;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import verso.config.Environment;
import verso.config.XMLConfigBuilder;
import verso.transaction.VTransactionSynchronizationAdapter;

public class VSessionFactory
{
	private Environment config;
	
	public static VSessionFactory getFactoryInstance(String resource) {
		try {
			VSessionFactory factory = new VSessionFactory();
			factory.config = XMLConfigBuilder.build(resource);
			return factory;
		} catch (IOException e) {
			System.err.println("Can't find resource " + resource);
		}
		return null;
	}
	public void setConfig(Environment config) {
	    this.config = config;
	}
	public Environment getConfig() {
	    return this.config;
	}
	
	public VSession openSession() {
	    return new VSession(config);
	}
}