package verso.session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import verso.mapper.MappedProxy;
import verso.mapper.MappedStatement;
import verso.transaction.VTransactionSynchronizationAdapter;

public class LazySession implements Session {
    
    private VSessionFactory sessionFactory;
    private Session proxy;
    
    public LazySession(VSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.proxy = (Session) Proxy.newProxyInstance(VSessionFactory.class.getClassLoader(),
                new Class[] { Session.class },
                new SessionProxy()); 
    }

    @Override
    public <T> T getDao(Class<T> clazz) {
	    return MappedProxy.newInstance(clazz, this);
    }

    @Override
    public void rollback() {
        proxy.rollback();        
    }

    @Override
    public void commit() {
        proxy.commit();        
    }
    
    @Override
    public void close() {
        proxy.close();        
    }
    

    @Override
    public Object select(MappedStatement mappedStmt, Object[] args) throws Exception {
        return proxy.select(mappedStmt, args);
    }

    @Override
    public Object other(MappedStatement mappedStmt, Object[] args) throws Exception {
        return proxy.other(mappedStmt, args);
    }
    
    class SessionProxy implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            VSession session = getSession();
            try {
                Object ret = method.invoke(session, args);
                return ret;
            } finally {
                if (!session.isSynchronizedWithTransaction()) {
                    session.commit();
                    session.close();
                } else {
                    session.released();
                }
            }
        }
    }
    
    private VSession getSession() {
        VSession session = (VSession) TransactionSynchronizationManager.getResource(sessionFactory);
        // 若存在并且带有事务，使用此session
        if (session != null && session.isSynchronizedWithTransaction()) {
            session.requested();
            return session;
        }
        session = sessionFactory.openSession();
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationAdapter adapter = new VTransactionSynchronizationAdapter(sessionFactory, session);
            TransactionSynchronizationManager.registerSynchronization(adapter);
            session.requested();
        }
        return session;
    }
}
