/*    */ package com.sun.xml.ws.tx.at.common;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*    */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSATVersion<T>
/*    */ {
/* 58 */   public static final WSATVersion<Notification> v10 = new WSATVersion10();
/* 59 */   public static final WSATVersion<Notification> v11 = new WSATVersion11();
/*    */   
/*    */   private Transactional.Version version;
/*    */   protected AddressingVersion addressingVersion;
/*    */   protected SOAPVersion soapVersion;
/*    */   
/*    */   public static WSATVersion getInstance(Transactional.Version version) {
/* 66 */     if (Transactional.Version.WSAT10 == version || Transactional.Version.DEFAULT == version)
/* 67 */       return v10; 
/* 68 */     if (Transactional.Version.WSAT11 == version || Transactional.Version.WSAT12 == version) {
/* 69 */       return v11;
/*    */     }
/* 71 */     throw new IllegalArgumentException(version + "is not a supported ws-at version");
/*    */   }
/*    */ 
/*    */   
/*    */   WSATVersion(Transactional.Version version) {
/* 76 */     this.version = version;
/*    */   }
/*    */   
/*    */   public abstract WSATHelper getWSATHelper();
/*    */   
/*    */   public AddressingVersion getAddressingVersion() {
/* 82 */     return this.addressingVersion;
/*    */   }
/*    */   
/*    */   public SOAPVersion getSOPAVersion() {
/* 86 */     return this.soapVersion;
/*    */   }
/*    */   
/*    */   public Transactional.Version getVersion() {
/* 90 */     return this.version;
/*    */   }
/*    */   
/*    */   public abstract CoordinatorProxyBuilder<T> newCoordinatorProxyBuilder();
/*    */   
/*    */   public abstract ParticipantProxyBuilder<T> newParticipantProxyBuilder();
/*    */   
/*    */   public abstract NotificationBuilder<T> newNotificationBuilder();
/*    */   
/*    */   public abstract EndpointReferenceBuilder newEndpointReferenceBuilder();
/*    */   
/*    */   public abstract WebServiceFeature newAddressingFeature();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\WSATVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */