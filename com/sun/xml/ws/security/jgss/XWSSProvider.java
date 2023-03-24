/*    */ package com.sun.xml.ws.security.jgss;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.security.Provider;
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
/*    */ 
/*    */ public final class XWSSProvider
/*    */   extends Provider
/*    */ {
/*    */   private static final long serialVersionUID = -238911724858694198L;
/*    */   private static final String INFO = "Sun (Kerberos v5, SPNEGO)";
/* 62 */   public static final XWSSProvider INSTANCE = new XWSSProvider();
/*    */ 
/*    */   
/*    */   public XWSSProvider() {
/* 66 */     super("XWSSJGSS", 1.0D, "Sun (Kerberos v5, SPNEGO)");
/*    */     
/* 68 */     AccessController.doPrivileged(new PrivilegedAction() {
/*    */           public Object run() {
/* 70 */             XWSSProvider.this.put("GssApiMechanism.1.2.840.113554.1.2.2", "com.sun.xml.ws.security.kerb.Krb5MechFactory");
/*    */             
/* 72 */             XWSSProvider.this.put("GssApiMechanism.1.3.6.1.5.5.2", "sun.security.jgss.spnego.SpNegoMechFactory");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 78 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\jgss\XWSSProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */