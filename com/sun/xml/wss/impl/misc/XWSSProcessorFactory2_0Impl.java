/*    */ package com.sun.xml.wss.impl.misc;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSProcessor;
/*    */ import com.sun.xml.wss.XWSSProcessorFactory;
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import java.io.InputStream;
/*    */ import javax.security.auth.callback.CallbackHandler;
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
/*    */ public class XWSSProcessorFactory2_0Impl
/*    */   extends XWSSProcessorFactory
/*    */ {
/*    */   public XWSSProcessor createProcessorForSecurityConfiguration(InputStream securityConfiguration, CallbackHandler handler) throws XWSSecurityException {
/* 56 */     return new XWSSProcessor2_0Impl(securityConfiguration, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\XWSSProcessorFactory2_0Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */