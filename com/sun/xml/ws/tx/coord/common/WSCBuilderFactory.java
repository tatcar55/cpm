/*    */ package com.sun.xml.ws.tx.coord.common;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Header;
/*    */ import com.sun.xml.ws.api.message.HeaderList;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationMessageBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationProxyBuilder;
/*    */ import com.sun.xml.ws.tx.coord.v10.WSCBuilderFactoryImpl;
/*    */ import com.sun.xml.ws.tx.coord.v11.WSCBuilderFactoryImpl;
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
/*    */ public abstract class WSCBuilderFactory
/*    */ {
/*    */   public static WSCBuilderFactory newInstance(Transactional.Version version) {
/* 53 */     if (Transactional.Version.WSAT10 == version || Transactional.Version.DEFAULT == version)
/* 54 */       return (WSCBuilderFactory)new WSCBuilderFactoryImpl(); 
/* 55 */     if (Transactional.Version.WSAT11 == version || Transactional.Version.WSAT12 == version) {
/* 56 */       return (WSCBuilderFactory)new WSCBuilderFactoryImpl();
/*    */     }
/* 58 */     throw new IllegalArgumentException(version + "is not a supported ws-at version");
/*    */   }
/*    */ 
/*    */   
/*    */   public static WSCBuilderFactory fromHeaders(HeaderList headers) {
/* 63 */     WSCBuilderFactory builder = null;
/* 64 */     for (int i = 0; i < headers.size(); i++) {
/* 65 */       Header header = headers.get(i);
/* 66 */       if (header.getLocalPart().equals("CoordinationContext")) {
/* 67 */         WSCBuilderFactoryImpl wSCBuilderFactoryImpl; if ("http://schemas.xmlsoap.org/ws/2004/10/wsat".equals(header.getNamespaceURI())) {
/* 68 */           WSCBuilderFactoryImpl wSCBuilderFactoryImpl1 = new WSCBuilderFactoryImpl();
/* 69 */         } else if ("http://docs.oasis-open.org/ws-tx/wsat/2006/06".equals(header.getNamespaceURI())) {
/* 70 */           wSCBuilderFactoryImpl = new WSCBuilderFactoryImpl();
/*    */         } 
/* 72 */         if (wSCBuilderFactoryImpl != null) {
/* 73 */           headers.understood(i);
/*    */           
/* 75 */           return (WSCBuilderFactory)wSCBuilderFactoryImpl;
/*    */         } 
/*    */       } 
/*    */     } 
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public abstract WSATCoordinationContextBuilder newWSATCoordinationContextBuilder();
/*    */   
/*    */   public abstract RegistrationProxyBuilder newRegistrationProxyBuilder();
/*    */   
/*    */   public abstract RegistrationMessageBuilder newWSATRegistrationRequestBuilder();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\WSCBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */