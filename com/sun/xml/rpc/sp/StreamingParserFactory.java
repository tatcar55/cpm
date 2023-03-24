/*    */ package com.sun.xml.rpc.sp;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public abstract class StreamingParserFactory
/*    */ {
/*    */   public static StreamingParserFactory newInstance() {
/* 59 */     return new StreamingParserFactoryImpl();
/*    */   }
/*    */   
/*    */   public abstract void setValidating(boolean paramBoolean);
/*    */   
/*    */   public abstract boolean isValidating();
/*    */   
/*    */   public abstract void setCoalescing(boolean paramBoolean);
/*    */   
/*    */   public abstract boolean isCoalescing();
/*    */   
/*    */   public abstract void setNamespaceAware(boolean paramBoolean);
/*    */   
/*    */   public abstract boolean isNamespaceAware();
/*    */   
/*    */   public abstract StreamingParser newParser(InputStream paramInputStream);
/*    */   
/*    */   public abstract StreamingParser newParser(File paramFile) throws IOException;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\StreamingParserFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */