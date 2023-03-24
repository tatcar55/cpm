/*    */ package com.sun.xml.ws.binding;
/*    */ 
/*    */ import com.sun.xml.ws.api.BindingID;
/*    */ import com.sun.xml.ws.client.HandlerConfiguration;
/*    */ import com.sun.xml.ws.resources.ClientMessages;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.handler.Handler;
/*    */ import javax.xml.ws.http.HTTPBinding;
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
/*    */ public class HTTPBindingImpl
/*    */   extends BindingImpl
/*    */   implements HTTPBinding
/*    */ {
/*    */   HTTPBindingImpl() {
/* 64 */     this(EMPTY_FEATURES);
/*    */   }
/*    */   
/*    */   HTTPBindingImpl(WebServiceFeature... features) {
/* 68 */     super(BindingID.XML_HTTP, features);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHandlerChain(List<Handler> chain) {
/* 78 */     for (Handler handler : chain) {
/* 79 */       if (!(handler instanceof javax.xml.ws.handler.LogicalHandler)) {
/* 80 */         throw new WebServiceException(ClientMessages.NON_LOGICAL_HANDLER_SET(handler.getClass()));
/*    */       }
/*    */     } 
/* 83 */     setHandlerConfig(new HandlerConfiguration(Collections.emptySet(), chain));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\binding\HTTPBindingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */