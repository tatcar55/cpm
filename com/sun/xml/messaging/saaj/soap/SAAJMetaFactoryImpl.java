/*    */ package com.sun.xml.messaging.saaj.soap;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.dynamic.SOAPFactoryDynamicImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.dynamic.SOAPMessageFactoryDynamicImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl;
/*    */ import com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl;
/*    */ import com.sun.xml.messaging.saaj.soap.ver1_2.SOAPFactory1_2Impl;
/*    */ import com.sun.xml.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.soap.MessageFactory;
/*    */ import javax.xml.soap.SAAJMetaFactory;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFactory;
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
/*    */ public class SAAJMetaFactoryImpl
/*    */   extends SAAJMetaFactory
/*    */ {
/* 56 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MessageFactory newMessageFactory(String protocol) throws SOAPException {
/* 62 */     if ("SOAP 1.1 Protocol".equals(protocol))
/* 63 */       return (MessageFactory)new SOAPMessageFactory1_1Impl(); 
/* 64 */     if ("SOAP 1.2 Protocol".equals(protocol))
/* 65 */       return (MessageFactory)new SOAPMessageFactory1_2Impl(); 
/* 66 */     if ("Dynamic Protocol".equals(protocol)) {
/* 67 */       return (MessageFactory)new SOAPMessageFactoryDynamicImpl();
/*    */     }
/* 69 */     log.log(Level.SEVERE, "SAAJ0569.soap.unknown.protocol", new Object[] { protocol, "MessageFactory" });
/*    */ 
/*    */ 
/*    */     
/* 73 */     throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating MessageFactory");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SOAPFactory newSOAPFactory(String protocol) throws SOAPException {
/* 80 */     if ("SOAP 1.1 Protocol".equals(protocol))
/* 81 */       return (SOAPFactory)new SOAPFactory1_1Impl(); 
/* 82 */     if ("SOAP 1.2 Protocol".equals(protocol))
/* 83 */       return (SOAPFactory)new SOAPFactory1_2Impl(); 
/* 84 */     if ("Dynamic Protocol".equals(protocol)) {
/* 85 */       return (SOAPFactory)new SOAPFactoryDynamicImpl();
/*    */     }
/* 87 */     log.log(Level.SEVERE, "SAAJ0569.soap.unknown.protocol", new Object[] { protocol, "SOAPFactory" });
/*    */ 
/*    */ 
/*    */     
/* 91 */     throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating SOAPFactory");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\SAAJMetaFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */