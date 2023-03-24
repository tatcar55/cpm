package com.sun.xml.rpc.processor.model.soap;

public interface SOAPTypeVisitor {
  void visit(SOAPArrayType paramSOAPArrayType) throws Exception;
  
  void visit(SOAPCustomType paramSOAPCustomType) throws Exception;
  
  void visit(SOAPEnumerationType paramSOAPEnumerationType) throws Exception;
  
  void visit(SOAPSimpleType paramSOAPSimpleType) throws Exception;
  
  void visit(SOAPAnyType paramSOAPAnyType) throws Exception;
  
  void visit(SOAPOrderedStructureType paramSOAPOrderedStructureType) throws Exception;
  
  void visit(SOAPUnorderedStructureType paramSOAPUnorderedStructureType) throws Exception;
  
  void visit(RPCRequestOrderedStructureType paramRPCRequestOrderedStructureType) throws Exception;
  
  void visit(RPCRequestUnorderedStructureType paramRPCRequestUnorderedStructureType) throws Exception;
  
  void visit(RPCResponseStructureType paramRPCResponseStructureType) throws Exception;
  
  void visit(SOAPListType paramSOAPListType) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPTypeVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */