package com.sun.xml.rpc.processor.model;

public interface ModelVisitor {
  void visit(Model paramModel) throws Exception;
  
  void visit(Service paramService) throws Exception;
  
  void visit(Port paramPort) throws Exception;
  
  void visit(Operation paramOperation) throws Exception;
  
  void visit(Request paramRequest) throws Exception;
  
  void visit(Response paramResponse) throws Exception;
  
  void visit(Fault paramFault) throws Exception;
  
  void visit(Block paramBlock) throws Exception;
  
  void visit(Parameter paramParameter) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\ModelVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */