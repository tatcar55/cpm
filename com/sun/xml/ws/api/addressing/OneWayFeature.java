/*     */ package com.sun.xml.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import java.net.URL;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public class OneWayFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://java.sun.com/xml/ns/jaxws/addressing/oneway";
/*     */   private String messageId;
/*     */   private WSEndpointReference replyTo;
/*     */   private WSEndpointReference sslReplyTo;
/*     */   private WSEndpointReference from;
/*     */   private WSEndpointReference faultTo;
/*     */   private WSEndpointReference sslFaultTo;
/*     */   private String relatesToID;
/*     */   private boolean useAsyncWithSyncInvoke = false;
/*     */   
/*     */   public OneWayFeature() {
/*  93 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OneWayFeature(boolean enabled) {
/* 103 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OneWayFeature(boolean enabled, WSEndpointReference replyTo) {
/* 113 */     this.enabled = enabled;
/* 114 */     this.replyTo = replyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled", "replyTo", "from", "relatesTo"})
/*     */   public OneWayFeature(boolean enabled, WSEndpointReference replyTo, WSEndpointReference from, String relatesTo) {
/* 127 */     this.enabled = enabled;
/* 128 */     this.replyTo = replyTo;
/* 129 */     this.from = from;
/* 130 */     this.relatesToID = relatesTo;
/*     */   }
/*     */   
/*     */   public OneWayFeature(AddressingPropertySet a, AddressingVersion v) {
/* 134 */     this.enabled = true;
/* 135 */     this.messageId = a.getMessageId();
/* 136 */     this.relatesToID = a.getRelatesTo();
/* 137 */     this.replyTo = makeEPR(a.getReplyTo(), v);
/* 138 */     this.faultTo = makeEPR(a.getFaultTo(), v);
/*     */   }
/*     */   
/*     */   private WSEndpointReference makeEPR(String x, AddressingVersion v) {
/* 142 */     if (x == null) return null; 
/* 143 */     return new WSEndpointReference(x, v);
/*     */   }
/*     */   
/*     */   public String getMessageId() {
/* 147 */     return this.messageId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 155 */     return "http://java.sun.com/xml/ns/jaxws/addressing/oneway";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSslEprs() {
/* 160 */     return (this.sslReplyTo != null || this.sslFaultTo != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public WSEndpointReference getReplyTo() {
/* 170 */     return this.replyTo;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getReplyTo(boolean ssl) {
/* 174 */     return (ssl && this.sslReplyTo != null) ? this.sslReplyTo : this.replyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReplyTo(WSEndpointReference address) {
/* 183 */     this.replyTo = address;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getSslReplyTo() {
/* 187 */     return this.sslReplyTo;
/*     */   }
/*     */   
/*     */   public void setSslReplyTo(WSEndpointReference sslReplyTo) {
/* 191 */     this.sslReplyTo = sslReplyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public WSEndpointReference getFrom() {
/* 201 */     return this.from;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrom(WSEndpointReference address) {
/* 210 */     this.from = address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getRelatesToID() {
/* 220 */     return this.relatesToID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRelatesToID(String id) {
/* 229 */     this.relatesToID = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpointReference getFaultTo() {
/* 238 */     return this.faultTo;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getFaultTo(boolean ssl) {
/* 242 */     return (ssl && this.sslFaultTo != null) ? this.sslFaultTo : this.faultTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultTo(WSEndpointReference address) {
/* 251 */     this.faultTo = address;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getSslFaultTo() {
/* 255 */     return this.sslFaultTo;
/*     */   }
/*     */   
/*     */   public void setSslFaultTo(WSEndpointReference sslFaultTo) {
/* 259 */     this.sslFaultTo = sslFaultTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseAsyncWithSyncInvoke() {
/* 268 */     return this.useAsyncWithSyncInvoke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseAsyncWithSyncInvoke(boolean useAsyncWithSyncInvoke) {
/* 277 */     this.useAsyncWithSyncInvoke = useAsyncWithSyncInvoke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSEndpointReference enableSslForEpr(@NotNull WSEndpointReference epr, @Nullable String sslHost, int sslPort) {
/* 293 */     if (!epr.isAnonymous()) {
/* 294 */       URL url; String address = epr.getAddress();
/*     */       
/*     */       try {
/* 297 */         url = new URL(address);
/* 298 */       } catch (Exception e) {
/* 299 */         throw new RuntimeException(e);
/*     */       } 
/* 301 */       String protocol = url.getProtocol();
/* 302 */       if (!protocol.equalsIgnoreCase("https")) {
/* 303 */         protocol = "https";
/* 304 */         String host = url.getHost();
/* 305 */         if (sslHost != null) {
/* 306 */           host = sslHost;
/*     */         }
/* 308 */         int port = url.getPort();
/* 309 */         if (sslPort > 0) {
/* 310 */           port = sslPort;
/*     */         }
/*     */         try {
/* 313 */           url = new URL(protocol, host, port, url.getFile());
/* 314 */         } catch (Exception e) {
/* 315 */           throw new RuntimeException(e);
/*     */         } 
/* 317 */         address = url.toExternalForm();
/* 318 */         return new WSEndpointReference(address, epr.getVersion());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 323 */     return epr;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\OneWayFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */