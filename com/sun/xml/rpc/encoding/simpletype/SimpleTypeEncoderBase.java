/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleTypeEncoderBase
/*    */   implements SimpleTypeEncoder
/*    */ {
/*    */   public abstract String objectToString(Object paramObject, XMLWriter paramXMLWriter) throws Exception;
/*    */   
/*    */   public abstract Object stringToObject(String paramString, XMLReader paramXMLReader) throws Exception;
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 46 */     writer.writeChars(objectToString(obj, writer));
/*    */   }
/*    */   
/*    */   public void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\SimpleTypeEncoderBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */