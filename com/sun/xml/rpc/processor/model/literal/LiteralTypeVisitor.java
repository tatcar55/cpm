package com.sun.xml.rpc.processor.model.literal;

public interface LiteralTypeVisitor {
  void visit(LiteralSimpleType paramLiteralSimpleType) throws Exception;
  
  void visit(LiteralSequenceType paramLiteralSequenceType) throws Exception;
  
  void visit(LiteralArrayType paramLiteralArrayType) throws Exception;
  
  void visit(LiteralAllType paramLiteralAllType) throws Exception;
  
  void visit(LiteralFragmentType paramLiteralFragmentType) throws Exception;
  
  void visit(LiteralEnumerationType paramLiteralEnumerationType) throws Exception;
  
  void visit(LiteralListType paramLiteralListType) throws Exception;
  
  void visit(LiteralIDType paramLiteralIDType) throws Exception;
  
  void visit(LiteralArrayWrapperType paramLiteralArrayWrapperType) throws Exception;
  
  void visit(LiteralAttachmentType paramLiteralAttachmentType) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralTypeVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */