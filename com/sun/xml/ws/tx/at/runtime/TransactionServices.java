package com.sun.xml.ws.tx.at.runtime;

import com.sun.xml.ws.tx.at.WSATException;
import javax.transaction.Synchronization;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.xml.ws.EndpointReference;

public interface TransactionServices {
  byte[] getGlobalTransactionId();
  
  Xid enlistResource(XAResource paramXAResource, Xid paramXid) throws WSATException;
  
  void registerSynchronization(Synchronization paramSynchronization, Xid paramXid) throws WSATException;
  
  Xid importTransaction(int paramInt, byte[] paramArrayOfbyte) throws WSATException;
  
  String prepare(byte[] paramArrayOfbyte) throws WSATException;
  
  void commit(byte[] paramArrayOfbyte) throws WSATException;
  
  void rollback(byte[] paramArrayOfbyte) throws WSATException;
  
  void replayCompletion(String paramString, XAResource paramXAResource) throws WSATException;
  
  EndpointReference getParentReference(Xid paramXid);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\runtime\TransactionServices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */