package com.sun.xml.ws.tx.at.common;

public interface CoordinatorIF<T> {
  void preparedOperation(T paramT);
  
  void abortedOperation(T paramT);
  
  void readOnlyOperation(T paramT);
  
  void committedOperation(T paramT);
  
  void replayOperation(T paramT);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\CoordinatorIF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */