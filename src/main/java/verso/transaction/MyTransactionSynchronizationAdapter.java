package verso.transaction;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;  
import org.springframework.transaction.support.TransactionSynchronizationManager;  

import verso.session.VSession;
import verso.session.VSessionFactory;
  
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter 
{
    private VSessionFactory sessionFactory;  
  
    public MyTransactionSynchronizationAdapter(VSessionFactory sessionFactory) {  
        this.sessionFactory = sessionFactory; 
    }  

    @Override  
    public void beforeCommit(boolean readOnly) {  
        if (!readOnly) {  
            VSession session = (VSession) TransactionSynchronizationManager.getResource(sessionFactory);  
            session.beginTransaction();  
        }  
    }  
  
    @Override  
    public void afterCompletion(int status) {  
        VSession session = (VSession) TransactionSynchronizationManager.getResource(sessionFactory);
        switch (status) {
        case STATUS_COMMITTED:
            session.commit();
            break;
        case STATUS_ROLLED_BACK:
            session.rollback();
            break;
        }
    }  
      
      
}  
