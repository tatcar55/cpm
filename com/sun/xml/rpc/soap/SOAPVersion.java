/*    */ package com.sun.xml.rpc.soap;
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
/*    */ public class SOAPVersion
/*    */ {
/*    */   private final String version;
/*    */   
/*    */   private SOAPVersion(String ver) {
/* 38 */     this.version = ver;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return this.version;
/*    */   }
/*    */   
/*    */   public boolean equals(String strVersion) {
/* 46 */     return this.version.equals(strVersion);
/*    */   }
/*    */   
/* 49 */   public static final SOAPVersion SOAP_11 = new SOAPVersion("soap1.1");
/* 50 */   public static final SOAPVersion SOAP_12 = new SOAPVersion("soap1.2");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */