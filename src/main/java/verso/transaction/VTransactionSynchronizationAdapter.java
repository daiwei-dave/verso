package verso.transaction;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;  
import org.springframework.transaction.support.TransactionSynchronizationManager;  

import verso.session.VSession;
import verso.session.VSessionFactory;
  
public class VTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter 
{
    private VSessionFactory sessionFactory;
    private VSession session;
  
    public VTransactionSynchronizationAdapter(VSessionFactory sessionFactory, VSession session) {  
        this.sessionFactory = sessionFactory;
        this.session = session;
        session.setSynchronizedWithTransaction(true);
        TransactionSynchronizationManager.bindResource(sessionFactory, session);
    }  

    @Override  
    public void beforeCommit(boolean readOnly) {  
        session.commit();
    }
      
    @Override  
    public void afterCompletion(int status) { 
        session.close();
        session.reset();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }      
}  
