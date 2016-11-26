package verso.session;

import java.io.IOException;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import verso.config.Environment;
import verso.config.XMLConfigBuilder;
import verso.transaction.MyTransactionSynchronizationAdapter;

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
	
    public VSession getSession() {  
        if (TransactionSynchronizationManager.hasResource(this)) {
            return (VSession) TransactionSynchronizationManager.getResource(this);
        }
        return openSession();
    }  
    
	public VSession openSession() {
	    VSession session = new VSession(config);
	    if (TransactionSynchronizationManager.isSynchronizationActive()) {
	        // 注册进当前线程管理一个Synchronization  
	        TransactionSynchronization transactionSynchronization = new MyTransactionSynchronizationAdapter(this);  
	        TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);  
	        // 绑定新开启的一个MySession进当前线程事务管理器  
	        TransactionSynchronizationManager.bindResource(this, session);
	    }
        return session;
	}
}