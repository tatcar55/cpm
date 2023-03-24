/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import com.sun.xml.rpc.encoding.literal.StandardLiteralTypeMappings;
/*    */ import com.sun.xml.rpc.encoding.soap.StandardSOAPTypeMappings;
/*    */ import com.sun.xml.rpc.soap.SOAPVersion;
/*    */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
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
/*    */ 
/*    */ public class StandardTypeMappings
/*    */ {
/*    */   public static ExtendedTypeMapping getSoap() {
/* 44 */     return getSoap(SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public static ExtendedTypeMapping getSoap(SOAPVersion ver) {
/*    */     try {
/* 49 */       return (ExtendedTypeMapping)new StandardSOAPTypeMappings(ver);
/* 50 */     } catch (Exception e) {
/* 51 */       throw new TypeMappingException("typemapping.nested.exception.static.initialization", new LocalizableExceptionAdapter(e));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ExtendedTypeMapping getLiteral() {
/*    */     try {
/* 59 */       return new TypeMappingImpl();
/* 60 */     } catch (Exception e) {
/* 61 */       throw new TypeMappingException("typemapping.nested.exception.static.initialization", new LocalizableExceptionAdapter(e));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ExtendedTypeMapping getRPCLiteral() {
/*    */     try {
/* 69 */       return (ExtendedTypeMapping)new StandardLiteralTypeMappings();
/* 70 */     } catch (Exception e) {
/* 71 */       throw new TypeMappingException("typemapping.nested.exception.static.initialization", new LocalizableExceptionAdapter(e));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\StandardTypeMappings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */