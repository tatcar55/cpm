/*    */ package com.sun.xml.ws.tx.coord.common.types;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseRegisterType<T extends EndpointReference, K>
/*    */ {
/*    */   protected K delegate;
/*    */   
/*    */   protected BaseRegisterType(K delegate) {
/* 77 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public K getDelegate() {
/* 81 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public abstract String getProtocolIdentifier();
/*    */   
/*    */   public abstract void setProtocolIdentifier(String paramString);
/*    */   
/*    */   public abstract T getParticipantProtocolService();
/*    */   
/*    */   public abstract void setParticipantProtocolService(T paramT);
/*    */   
/*    */   public abstract List<Object> getAny();
/*    */   
/*    */   public abstract Map<QName, String> getOtherAttributes();
/*    */   
/*    */   public abstract boolean isDurable();
/*    */   
/*    */   public abstract boolean isVolatile();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\BaseRegisterType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */