package com.sun.xml.rpc.encoding;

public interface SOAPInstanceBuilder {
  public static final int GATES_CONSTRUCTION = 1;
  
  public static final int GATES_INITIALIZATION = 2;
  
  public static final int REQUIRES_CREATION = 4;
  
  public static final int REQUIRES_INITIALIZATION = 8;
  
  public static final int REQUIRES_COMPLETION = 16;
  
  int memberGateType(int paramInt);
  
  void construct();
  
  void setMember(int paramInt, Object paramObject);
  
  void initialize();
  
  void setInstance(Object paramObject);
  
  Object getInstance();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPInstanceBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */