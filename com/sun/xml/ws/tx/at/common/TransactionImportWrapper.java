package com.sun.xml.ws.tx.at.common;

import javax.resource.spi.XATerminator;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;

public interface TransactionImportWrapper {
  void recreate(Xid paramXid, long paramLong);
  
  void release(Xid paramXid);
  
  XATerminator getXATerminator();
  
  int getTransactionRemainingTimeout() throws SystemException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\TransactionImportWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */